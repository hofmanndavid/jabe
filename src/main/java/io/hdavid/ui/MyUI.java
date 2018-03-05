package io.hdavid.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import io.hdavid.crosscut.UserAccessControl;
import io.hdavid.entity.query.QUser;

//@Viewport("user-scalable=no,initial-scale=1.0") ??
@Title("JABE")
@Theme("custom")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        if (true) { // just while testing
            if (UserAccessControl.getCurrent() == null)
                UserAccessControl.setLoggedInUserForCurrentSession(new QUser().username.eq("admin").findOne());
            showMainView();
            return;
        }


        if (UserAccessControl.getCurrent() == null) {
            setContent(new LoginView(() -> showMainView() ));
        } else {
            showMainView();
        }

    }

    private void showMainView() {

        setContent(new MainView());
    }

    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class VaadinUiServlet extends VaadinServlet {

    }

}
