package io.hdavid.crosscut;

import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import io.ebean.migration.MigrationConfig;
import io.ebean.migration.MigrationRunner;
import io.hdavid.entity.User;
import io.hdavid.entity.query.QUser;
import javax.sql.DataSource;

public class EBeanConfig {

    public static void configure() {

        DataSource ds = DbPool.getDs();

//        System.out.println("Init DB Migration");
        // run any pending migrations
        new MigrationRunner(new MigrationConfig()).run(ds); // begin/commits transaction for the migration...

        // Configures ebean server
        ServerConfig config = new ServerConfig();
        config.setDataSource(ds);
        config.addPackage(User.class.getPackage().getName());
        config.setUseJtaTransactionManager(false);
        EbeanServerFactory.create(config);// used in multiple threads... no prob. trx are  managed in a thread local storage and or in the jta transaction registry.

    }
}
