package org.kaismh.solr;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

/**
 * Created by kaismh on 7/24/16.
 */
public class DBElevate implements Serializable {

    private static Logger log = LoggerFactory.getLogger(DBElevate.class);

    private String id;
    private String elevation_query_text;

    public DBElevate() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getElevation_query_text() {
        return elevation_query_text;
    }

    public void setElevation_query_text(String elevation_query_text) {
        this.elevation_query_text = elevation_query_text;
    }

    @Override
    public String toString() {
        return "DBElevate{" +
                "id='" + id + '\'' +
                ", elevation_query_text='" + elevation_query_text + '\'' +
                '}';
    }

    @SuppressWarnings("unchecked")
    public static List<DBElevate> getData(String sqlQuery, ElevateDataSource dataSource) {

        Connection conn = null;
        List<DBElevate> dbElevateList = null;

        QueryRunner queryRunner = new QueryRunner();

        try {
            conn = dataSource.getConnection();
            dbElevateList = (List) queryRunner.query(conn, sqlQuery, new BeanListHandler(DBElevate.class));

        } catch (Exception se) {
            log.error(se.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);

        }
        return dbElevateList;
    }
}
