package io.hdavid.crosscut;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DbPool {
    private static volatile HikariDataSource ds;
    public static void configure() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:jabe.h2db"); //jdbc:h2:[file:][<path>]<databaseName>
        config.setUsername("sa"); // systemadmin?
        config.setPassword("sa");
//        config.addDataSourceProperty("", "");

        ds = new HikariDataSource(config);
    }

    public static DataSource getDs() {
        return ds;
    }

}
