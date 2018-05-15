package io.hdavid;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class BlogProperties {

    public static final String adminUser() { return p("adminUser", String.class); }
    public static final String adminPass() { return p("adminPass", String.class); }

    // NO meed to touch below this line
    private static volatile long lastConfModifiedTime = 0;
    private static final File configFile = new File("settings/blog.properties");
    private static final Properties prop = new Properties();
    @SneakyThrows
    private static <T> T p(String p, Class<T> classToConvert) {
        synchronized (prop) {
            if (lastConfModifiedTime != configFile.lastModified()) {
                lastConfModifiedTime = configFile.lastModified();
                @Cleanup FileInputStream fis = new FileInputStream(configFile);
                prop.clear();
                prop.load(fis);
            }
        }
        Object o = prop.get(p);

        if (classToConvert.isAssignableFrom(String.class))
            return classToConvert.cast(o);

        throw new RuntimeException("Problem reading configuration file");

    }

}
