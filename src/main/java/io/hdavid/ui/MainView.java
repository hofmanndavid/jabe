package io.hdavid.ui;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.*;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import lombok.SneakyThrows;
import org.vaadin.teemusa.sidemenu.SideMenu;

import java.lang.reflect.Field;

public class MainView extends SideMenu {

    public MainView() {
        Page.getCurrent().getUI().setNavigator(new Navigator(Page.getCurrent().getUI(), this));
        Page.getCurrent().getUI().getNavigator().setErrorView(new ErrorView()); // NOTE: Navigation and custom code menus should not be mixed. See issue #8 ????????

        addMenuItemWithNavigation("Posts", PostsView.class);
        addMenuItemWithNavigation("Users", UsersView.class);
        addMenuItemWithNavigation("About", AboutView.class);

            Page.getCurrent().getUI().getNavigator().navigateTo(
                    Page.getCurrent().getUI().getNavigator().getState() != null ?
                            Page.getCurrent().getUI().getNavigator().getState() :
                            PostsView.NAME);

        setMenuCaption("Jabe");

        setUserName("Username");
        setUserIcon(VaadinIcons.MALE);
//        clearUserMenu();
        addUserMenuItem("Settings", VaadinIcons.WRENCH, () -> Notification.show("Showing settings", Notification.Type.TRAY_NOTIFICATION));
        addUserMenuItem("Sign out", () -> Notification.show("Logging out..", Notification.Type.TRAY_NOTIFICATION));

        // esto es un item de menu que no participa en la navegacion
//        addMenuItem("Show/Hide user menu", VaadinIcons.USER, () -> setUserMenuVisible( !isUserMenuVisible()));
    }

    @SneakyThrows
    private MenuRegistration addMenuItemWithNavigation(String caption, Class<? extends View> viewClass) {
        Field viewNameField = viewClass.getField("NAME");
        String viewName = (String) viewNameField.get(viewClass);
        Page.getCurrent().getUI().getNavigator().addView(viewName, viewClass);
        MenuRegistration mr = addNavigation(caption, viewName);
        return mr;
    }


}
