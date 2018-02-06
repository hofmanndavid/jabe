package io.hdavid;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DefaultServletConfig;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import io.undertow.servlet.handlers.DefaultServlet;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;

public class App {

    @SneakyThrows
    public static void main( String[] args ) {

        DeploymentInfo servletBuilder = Servlets.deployment()
                .setClassLoader(App.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("jabe.war") // really need a name here?
                .setDefaultSessionTimeout(60)
                .addServlets(
                        Servlets.servlet("VaadinUiServlet", VaadinUiServlet.class)
//                                .addInitParam("vaadin.production", "false") // anotated in MuUI.java
                                .setAsyncSupported(true)
                                .addMapping("/app/*")
                                .addMapping("/VAADIN/*"),
                        Servlets.servlet("MessageServlet", MessageServlet.class)
                                .addInitParam("message", "Hello World")
                                .addMapping("/sayhi"),
                        Servlets.servlet("default", DefaultServlet.class)
                                .addInitParam(DefaultServlet.DIRECTORY_LISTING, "true")
                                .addInitParam(DefaultServlet.DEFAULT_ALLOWED, "true")
                                .addInitParam(DefaultServlet.ALLOW_POST, "false")
                                .addInitParam(DefaultServlet.RESOLVE_AGAINST_CONTEXT_ROOT, "true"))
                ;

        servletBuilder.setResourceManager(new ClassPathResourceManager(App.class.getClassLoader(), "webstatic"));

        ServletContainer servletContainer = Servlets.defaultContainer();
        DeploymentManager manager = servletContainer.addDeployment(servletBuilder);
        manager.deploy();

        HttpHandler start = manager.start();
        PathHandler path = Handlers.path(Handlers.redirect("/"))
                .addPrefixPath("/", start);

        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(path)
                .build();
        server.start();
    }
}
