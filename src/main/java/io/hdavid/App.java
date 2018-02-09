package io.hdavid;

import io.ebean.Ebean;
import io.hdavid.crosscut.DbPool;
import io.hdavid.crosscut.EBeanConfig;
import io.hdavid.crosscut.ServletConfig;
import lombok.SneakyThrows;

public class App {

    @SneakyThrows
    public static void main( String[] args ) {

        DbPool.configure();
        EBeanConfig.configure();
        ServletConfig.configure();

    }
}
