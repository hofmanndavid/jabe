package io.hdavid.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

@Theme("custom")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        setContent(new LoginView());
    }

    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class VaadinUiServlet extends VaadinServlet {

    }

}
