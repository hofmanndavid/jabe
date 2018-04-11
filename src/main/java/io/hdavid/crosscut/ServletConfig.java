package io.hdavid.crosscut;

import io.hdavid.App;
import io.hdavid.blogservice.BlogServlet;
import io.hdavid.ui.MyUI;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import io.undertow.servlet.handlers.DefaultServlet;
import lombok.SneakyThrows;

public class ServletConfig {

    @SneakyThrows
    public static void configure() {
        DeploymentInfo servletBuilder = Servlets.deployment()
                .setClassLoader(App.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("jabe.war") // really need a name here?
                .setDefaultSessionTimeout(60*60)
                .addServlets(
                        Servlets.servlet("VaadinUiServlet", MyUI.VaadinUiServlet.class)
//                                .addInitParam("vaadin.production", "false") // anotated in MuUI.java
                                .setAsyncSupported(true)
                                .addMapping("/admin/*")
                                .addMapping("/VAADIN/*"),
                        Servlets.servlet("BlogServlet", BlogServlet.class)
//                                .addInitParam("", "")
                                .addMapping("/*")
//                        ,
//                        Servlets.servlet("default", DefaultServlet.class)
//                                .addInitParam(DefaultServlet.DIRECTORY_LISTING, "false")
//                                .addInitParam(DefaultServlet.DEFAULT_ALLOWED, "true")
//                                .addInitParam(DefaultServlet.ALLOW_POST, "false")
//                                .addInitParam(DefaultServlet.RESOLVE_AGAINST_CONTEXT_ROOT, "true")
                )
//                .addWelcomePage("index.html")
                ;

        servletBuilder.setResourceManager(new ClassPathResourceManager(App.class.getClassLoader(), "webstatic"));

        ServletContainer servletContainer = Servlets.defaultContainer();
        DeploymentManager manager = servletContainer.addDeployment(servletBuilder);
        manager.deploy();

        HttpHandler start = manager.start();
//        PathHandler path = Handlers.path(Handlers.redirect("/")) // why do we need this?
//                .addPrefixPath("/", start);

        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(start)//path)
                .setIoThreads(3)
                .setWorkerThreads(3)
                .build();
        server.start();
    }

}
