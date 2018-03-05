package io.hdavid.crosscut;

import com.vaadin.server.VaadinSession;
import io.hdavid.entity.User;

public class UserAccessControl {

    public static void setLoggedInUserForCurrentSession(User user) {
        if (user == null)
            throw new IllegalArgumentException();

        if (VaadinSession.getCurrent().getAttribute(User.class.getName())!= null)
            throw new RuntimeException("User already assigned.");

        VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
    }

    public static User getCurrent() {
        return (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
    }
}
