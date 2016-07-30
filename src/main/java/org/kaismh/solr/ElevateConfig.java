package org.kaismh.solr;

import java.io.Serializable;

/**
 * Created by kaismh on 7/23/16.
 */
class ElevateConfig implements Serializable {
    private boolean enabled = true;
    private boolean overwrite = true;
    private boolean dataDir = true;
    private String elevateOutputFile="elevate.xml";
    private String splitRegex="";
    private String driverClassName = null;
    private String password = null;
    private String url = null;
    private String username = null;
    private String sql = null;


    public ElevateConfig() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getElevateOutputFile() {
        return elevateOutputFile;
    }

    public void setElevateOutputFile(String elevateOutputFile) {
        this.elevateOutputFile = elevateOutputFile;
    }

    public String getSplitRegex() {
        return splitRegex;
    }

    public void setSplitRegex(String splitRegex) {
        this.splitRegex = splitRegex;
    }


    public boolean isDataDir() {
        return dataDir;
    }

    public void setDataDir(boolean dataDir) {
        this.dataDir = dataDir;
    }

    @Override
    public String toString() {
        return "ElevateConfig{" +
                "enabled=" + enabled +
                ", overwrite=" + overwrite +
                ", dataDir=" + dataDir +
                ", elevateOutputFile='" + elevateOutputFile + '\'' +
                ", splitRegex='" + splitRegex + '\'' +
                ", driverClassName='" + driverClassName + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}
