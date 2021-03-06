package io.hdavid.ui;

import com.vaadin.ui.*;
import io.hdavid.crosscut.UserAccessControl;
import io.hdavid.entity.query.QUser;
import io.hdavid.util.Callback;

import static net.hdavid.easylayout.L.*;

public class LoginView extends VerticalLayout {

    TextField username = new TextField("Username");
    PasswordField password = new PasswordField("Password");
    Button login = new Button("Password");
    final Callback onUserLogin;
    public LoginView(Callback onUserLogin) {
        this.onUserLogin = onUserLogin;

        login.addClickListener(cl-> {
           if (!username.getValue().equals("asdf")) {
               Notification.show("Credenciales invalidas");
               password.setValue("");
               username.setValue("");
               username.focus();
               return;
           }

           UserAccessControl.setLoggedInUserForCurrentSession(new QUser().username.eq(username.getValue()).findOne());
           onUserLogin.call();


        });

        ve(this, _FULL_SIZE, _MARGIN, _EXPANDER, ve(username, password, login, Alignment.BOTTOM_RIGHT),_EXPANDER);
    }

}
