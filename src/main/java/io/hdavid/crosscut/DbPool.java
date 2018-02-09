package io.hdavid.config;

import com.zaxxer.hikari.HikariConfig;

public class DbPoolConfig {

    public static void configure() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:h2db"); //jdbc:h2:[file:][<path>]<databaseName>
        config.setUsername("sa");
        config.setPassword("sa");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource ds = new HikariDataSource(config);
    }
}
