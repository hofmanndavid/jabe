package io.hdavid.jabe;

import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import io.ebean.dbmigration.DbMigration;
import io.hdavid.crosscut.DbPool;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateSchemaMigrations {

    @SneakyThrows
    public static void main(String[] args ) {

        DbPool.configure();
        DataSource ds = DbPool.getDs();

//        System.setProperty("ddl.migration.version", "1.0");// optional & auto-increment
        String messageIteration = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        System.setProperty("ddl.migration.name", "DbMig"+ messageIteration);

        ServerConfig sc = new ServerConfig();
        sc.setDataSource(ds);
        sc.addPackage(User.class.getPackage().getName());
        EbeanServer es = EbeanServerFactory.create(sc);

        DbMigration dbMigration = DbMigration.create();
        dbMigration.setServer(es);
        dbMigration.generateMigration(); // generate the migration ddl and xml  with EbeanServer in "offline" mode
    }
}

