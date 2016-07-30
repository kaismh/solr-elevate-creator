package org.kaismh.solr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kaismh on 7/24/16.
 */
public class XMLWriter {

    private static Logger log = LoggerFactory.getLogger(XMLWriter.class);

    private final ElevateConfig elevateConfig;
    private final List<DBElevate> dbElevateList;

    public XMLWriter(ElevateConfig elevateConfig, List<DBElevate> dbElevateList) {
        this.elevateConfig = elevateConfig;
        this.dbElevateList = dbElevateList;
    }

    public Document getDocument() {
        boolean split = !Utils.isBlank(elevateConfig.getSplitRegex());
        DocumentBuilder docBuilder;
        HashMap<String, List<String>> queryHashMap = new HashMap<>();

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {

            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error(e.getMessage());
            return null;
        }

        Document doc = docBuilder.newDocument();
        doc.setXmlStandalone(true);

        Element rootElement = doc.createElement("elevate");
        doc.appendChild(rootElement);

        for (DBElevate dbElevate : dbElevateList) {

            String[] queryList;
            if (split) {
                queryList = dbElevate.getElevation_query_text().split(elevateConfig.getSplitRegex());
            } else {
                queryList = new String[]{dbElevate.getElevation_query_text()};
            }

            for (String queryText : queryList) {
                if (Utils.isBlank(queryText)) {
                    continue;
                }

                if (queryHashMap.containsKey(queryText)) {
                    queryHashMap.get(queryText).add(dbElevate.getId());
                } else {
                    ArrayList<String> listIds = new ArrayList<>();
                    listIds.add(dbElevate.getId());
                    queryHashMap.put(queryText, listIds);
                }
            }

        }

        for (String query : queryHashMap.keySet()) {
            addQuery(doc, rootElement, query, queryHashMap.get(query));
        }

        return doc;
    }

    private void addQuery(Document doc, Element rootElement, String queryText, List<String> ids) {

        Element queryElement = getQueryElement(doc, queryText);

        for (String id : ids) {
            queryElement.appendChild(getDocElement(doc, id));
        }

        rootElement.appendChild(queryElement);
    }

    private Element getDocElement(Document doc, String id) {

        Element docElement = doc.createElement("doc");
        Attr docAttribute = doc.createAttribute("id");
        docAttribute.setValue(id);
        docElement.setAttributeNode(docAttribute);

        return docElement;
    }

    private Element getQueryElement(Document doc, String queryText) {

        Element queryElement = doc.createElement("query");
        Attr textAttribute = doc.createAttribute("text");
        textAttribute.setValue(queryText);
        queryElement.setAttributeNode(textAttribute);

        return queryElement;

    }

    public boolean saveFile(String fileName, Document doc) {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            log.error(e.getMessage());
            return false;
        }
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");

        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(new File(fileName));

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            log.error(e.getMessage());
            return false;
        }

        return true;
    }
}
