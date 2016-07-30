package org.kaismh.solr;

import org.apache.solr.cloud.CloudUtil;
import org.apache.solr.handler.dataimport.Context;
import org.apache.solr.handler.dataimport.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by kaismh on 7/22/16.
 */
public class ElevateFileCreatorPlugin implements EventListener {

    private static final String PROP_FILE_NAME = "elevate-creator.properties";
    private static Logger log = LoggerFactory.getLogger(ElevateFileCreatorPlugin.class);

    /**
     * Event callback
     *
     * @param context the Context in which this event was called
     */
    @Override
    public void onEvent(Context context) {

        log.debug("Starting Solr Elevate File Creator.");

        String resourcePath = CloudUtil.unifiedResourcePath(context.getSolrCore().getSolrConfig().getResourceLoader());
        String absoluteDataPath = new File(context.getSolrCore().getDataDir()).getAbsolutePath();
        String absoluteResourcePath=new File(resourcePath).getAbsolutePath();
        String configFile = absoluteResourcePath +File.separator + PROP_FILE_NAME;
        String outFileName;

        ElevateConfig elevateConfig= ElevateConfigFactory.getElevateConfig(configFile);
        if(elevateConfig==null) {
            return;
        }

        if(!elevateConfig.isEnabled()) {
            log.debug("Solr Elevate File Creator Plugin is disabled, nothing to do.");
            return;
        }

        if(elevateConfig.isDataDir()) {
            outFileName =absoluteDataPath +File.separator + elevateConfig.getElevateOutputFile();
        } else {
            outFileName =absoluteResourcePath +File.separator + elevateConfig.getElevateOutputFile();
        }

        if(!elevateConfig.isOverwrite() && (new File(outFileName)).exists()) {
            log.info("File: " + outFileName + " already exists and overwrite option is false.");
            return;
        }

        ElevateDataSource dataSource = new ElevateDataSource(elevateConfig.getDriverClassName(),
                elevateConfig.getUsername(),elevateConfig.getPassword(),elevateConfig.getUrl());

        List<DBElevate> dbElevateList=DBElevate.getData(elevateConfig.getSql(),dataSource);
        if(dbElevateList==null) {
            return;
        }

        try {
            dataSource.getDataSource().close();
        } catch (SQLException e) {

        }

        XMLWriter writer = new XMLWriter(elevateConfig,dbElevateList);
        Document document= writer.getDocument();
        if(document==null) {
            return;
        }

        boolean saveResult= writer.saveFile(outFileName,document);

        if(!saveResult) return;
        log.info(outFileName + " was generated and saved successfully.");

    }
}
