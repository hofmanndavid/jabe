package io.hdavid.util;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public abstract class CommonWindow extends Window {

	protected void initAndShow(String caption, Component content) {
		setModal(true);
		addCloseShortcut(KeyCode.ESCAPE);
		setCaption(caption);
		UI.getCurrent().addWindow(this);
		if (content.getWidthUnits() == Unit.PERCENTAGE && content.getWidth() == 100 &&
				content.getHeightUnits() == Unit.PERCENTAGE && content.getHeight() == 100) {
			setSizeFull();
		}
		setContent(content);
		center();
	}

}
