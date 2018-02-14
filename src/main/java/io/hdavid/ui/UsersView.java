package io.hdavid.ui;

import com.vaadin.navigator.View;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Date;

public class UsersView extends HorizontalLayout implements View {
    public static final String NAME = "users";
    public UsersView() {
        Label label = new Label("Here the users view page will be implemented"+ new Date());
        addComponent(label);
    }

}
