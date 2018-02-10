package io.hdavid.ui;

import com.vaadin.navigator.View;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Date;

public class AboutView extends HorizontalLayout implements View {
    public static final String NAME = "about";
    public AboutView() {
        Label label = new Label("About Page here "+ new Date());
        label.addStyleName(ValoTheme.LABEL_H1);
        addComponent(label);
    }

}
