package io.hdavid.ui;

import com.vaadin.navigator.View;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

public class ErrorView extends HorizontalLayout implements View {

    public ErrorView() {
        Label label = new Label("Error page leka");
        label.addStyleName(ValoTheme.LABEL_H1);
        addComponent(label);
    }

}
