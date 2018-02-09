package io.hdavid.ui;

import com.vaadin.ui.*;

import static net.hdavid.easylayout.L.*;

public class LoginView extends VerticalLayout {

    TextField username = new TextField("Username");
    PasswordField password = new PasswordField("Password");
    Button login = new Button("Password");

    public LoginView() {


        ve(this, _MARGIN, _EXPANDER, ve(username, password, login, Alignment.BOTTOM_RIGHT),_EXPANDER);
    }



}
