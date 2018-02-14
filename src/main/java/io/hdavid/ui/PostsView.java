package io.hdavid.ui;

import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import static net.hdavid.easylayout.L.*;

public class PostsView extends VerticalLayout implements View {

    public static final String NAME = "posts";

    public PostsView() {

        ve(this,new Label("vista de posts"));
    }
}
