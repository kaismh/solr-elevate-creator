package org.kaismh.solr;

import com.github.davidcarboni.ResourceUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by kaismh on 7/24/16.
 */
public class DataBaseTest {

    @Ignore("Change dbTestPostgres.properties and dbTestMysql.properties to match your DB before running this Test")
    @Test
    public void dbTest() throws IOException, SQLException {
        testPostgres();
        testMysql();

    }

    public void testMysql() throws IOException, SQLException {
        performTest("/dbTestMysql.properties");
    }

    public void testPostgres() throws IOException, SQLException {

        performTest("/dbTestPostgres.properties");
    }

    public void performTest(String propertiesFile) throws IOException, SQLException {

        String outFileName="/tmp/";

        ElevateConfig elevateConfig= ElevateConfigFactory.getElevateConfig(
                ResourceUtils.getFile(propertiesFile));

        Assert.assertNotNull(elevateConfig);

        if(!elevateConfig.isEnabled()) {
            System.out.println("Solr Elevate File Creator Plugin is disabled, nothing to do.");
            return;
        }

        outFileName+=elevateConfig.getElevateOutputFile();

        if(!elevateConfig.isOverwrite() && (new File(outFileName)).exists()) {
            System.out.println("File: " + outFileName + " already exists and overwrite option is false.");
            return;
        }

        ElevateDataSource dataSource = new ElevateDataSource(elevateConfig.getDriverClassName(),
                elevateConfig.getUsername(),elevateConfig.getPassword(),elevateConfig.getUrl());

        List<DBElevate> dbElevateList=DBElevate.getData(elevateConfig.getSql(),dataSource);

        dataSource.getDataSource().close();

        Assert.assertNotNull(dbElevateList);

        XMLWriter writer = new XMLWriter(elevateConfig,dbElevateList);
        Document document= writer.getDocument();

        Assert.assertNotNull(document);

        boolean saveResult= writer.saveFile(outFileName,document);
        Assert.assertEquals(true,saveResult);

    }


}
