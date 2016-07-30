package org.kaismh.solr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created by kaismh on 7/23/16.
 */
class ElevateConfigFactory {

    private static Logger log = LoggerFactory.getLogger(ElevateConfigFactory.class);

    private static final String PROP_ENABLED = "enabled";
    private static final String PROP_OVERWRITE = "overwrite";
    private static final String PROP_DATA_DIR = "dataDir";
    private static final String PROP_OUTPUT_FILE = "elevateOutputFile";
    private static final String PROP_SPLIT_REGEX = "splitRegex";
    private static final String PROP_DRIVER = "driverClassName";
    private static final String PROP_USERNAME = "username";
    private static final String PROP_PASSWORD = "password";
    private static final String PROP_URL = "url";
    private static final String PROP_SQL = "sql";

    private static final String[] requiredProperties={PROP_DRIVER,PROP_USERNAME,PROP_PASSWORD,PROP_URL,PROP_SQL};

    public static ElevateConfig getElevateConfig(String configFile) {

        File file = new File(configFile);
        return getElevateConfig(file);
    }
    public static ElevateConfig getElevateConfig(File file) {

        boolean found = file.isFile() && file.canRead();

        if(!found) {
            log.info("Could not find configuration file at the following location: " + file.getAbsolutePath());
            return null;
        }

        Properties properties = new Properties();
        InputStream inputStream;

        try {
            inputStream = new FileInputStream(file);
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Could not read configuration file from the following location: " + file.getAbsolutePath() + ", " + e.getMessage());
            return null;
        }

        return getConfig(properties);

    }

    private static ElevateConfig getConfig(Properties properties) {

        ElevateConfig elevateConfig = new ElevateConfig();

        for(String prop:requiredProperties) {
            if(!properties.containsKey(prop)) {
                log.error("Property " + prop +" is mandatory. You need to add it the configuration file.");
                return null;
            }
        }

        elevateConfig.setEnabled(Boolean.parseBoolean(properties.getProperty(
                PROP_ENABLED,String.valueOf(elevateConfig.isEnabled()))));
        elevateConfig.setOverwrite(Boolean.parseBoolean(properties.getProperty(
                PROP_OVERWRITE,String.valueOf(elevateConfig.isOverwrite()))));
        elevateConfig.setDataDir(Boolean.parseBoolean(properties.getProperty(
                PROP_DATA_DIR,String.valueOf(elevateConfig.isDataDir()))));

        elevateConfig.setElevateOutputFile(properties.getProperty(PROP_OUTPUT_FILE, elevateConfig.getElevateOutputFile()));
        elevateConfig.setSplitRegex(properties.getProperty(PROP_SPLIT_REGEX, elevateConfig.getSplitRegex()));

        elevateConfig.setDriverClassName(properties.getProperty(PROP_DRIVER));
        elevateConfig.setUsername(properties.getProperty(PROP_USERNAME));
        elevateConfig.setPassword(properties.getProperty(PROP_PASSWORD));
        elevateConfig.setUrl(properties.getProperty(PROP_URL));
        elevateConfig.setSql(properties.getProperty(PROP_SQL));

        return elevateConfig;

    }
}
