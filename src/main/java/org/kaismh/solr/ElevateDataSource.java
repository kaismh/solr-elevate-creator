package org.kaismh.solr;


import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by kaismh on 7/23/16.
 */


class ElevateDataSource {

    private BasicDataSource ds;


    public ElevateDataSource(String driverClassName, String username,
                             String password, String url) {
        ds = new BasicDataSource();

        ds.setDriverClassName(driverClassName);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setUrl(url);

        ds.setMaxActive(50);
        ds.setMaxIdle(50);
        ds.setMaxWait(10000);
        ds.setValidationQuery("/* ping */ SELECT 1");
        ds.setTestWhileIdle(true);

    }

    public Connection getConnection() throws SQLException {
        return this.ds.getConnection();
    }


    public BasicDataSource getDataSource() {
        return this.ds;
    }


}
