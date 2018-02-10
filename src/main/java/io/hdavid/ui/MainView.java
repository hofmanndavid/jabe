package io.hdavid.ui;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.teemusa.sidemenu.SideMenu;

public class MainView extends SideMenu {

    private MenuRegistration menuToRemove;
    private MenuRegistration subSubTreeItem;

    private boolean logoVisible = true;
    private ThemeResource logo = new ThemeResource("images/linux-penguin.png");

    private String menuCaption = "SideMenu Add-on";

    public MainView() {

        Navigator navigator = new Navigator(Page.getCurrent().getUI(), this);
        Page.getCurrent().getUI().setNavigator(navigator);
        // NOTE: Navigation and custom code menus should not be mixed. See issue #8


        navigator.setErrorView(new ErrorView());

        // Navigation examples
        navigator.addView(AboutView.NAME, AboutView.class);
        addNavigation("AboutView", AboutView.NAME);
        navigator.addView(UsersView.NAME, UsersView.class);
        addNavigation("UsersViewHere", VaadinIcons.AMBULANCE, UsersView.NAME);

        // Since we're mixing both navigator and non-navigator menus the navigator state needs to be manually triggered.
        navigator.navigateTo(AboutView.NAME);

        setMenuCaption("SideMenu Add-on", logo);

        // Arbitrary method execution
        addMenuItem("My Menu Entry", () -> {
            VerticalLayout content = new VerticalLayout();
            content.addComponent(new Label("A layout"));
            setContent(content);
        });
        MenuRegistration mr = addMenuItem("Entry With Icon", VaadinIcons.ACCESSIBILITY, () -> {
            VerticalLayout content = new VerticalLayout();
            content.addComponent(new Label("Another layout"));
            setContent(content);
        });
        mr.select();// (???)  Navigator has done its own setup, any menu can be selected.

        // User menu controls
        addMenuItem("Show/Hide user menu", VaadinIcons.USER, () -> setUserMenuVisible( !isUserMenuVisible()));

        menuToRemove = addMenuItem("Remove this menu item", () -> {
            if (menuToRemove != null) {
                menuToRemove.remove();
                menuToRemove = null;
            }
        });

//        initTreeMenu();

        setUser("Guest", VaadinIcons.MALE);
    }

    private void setUser(String name, Resource icon) {
        setUserName(name);
        setUserIcon(icon);

        clearUserMenu();
        addUserMenuItem("Settings", VaadinIcons.WRENCH, () -> Notification.show("Showing settings", Notification.Type.TRAY_NOTIFICATION));
        addUserMenuItem("Sign out", () -> Notification.show("Logging out..", Notification.Type.TRAY_NOTIFICATION));

        addUserMenuItem("Hide logo", () -> {
            if (!logoVisible) {
                setMenuCaption(menuCaption, logo);
            } else {
                setMenuCaption(menuCaption);
            }
            logoVisible = !logoVisible;
        });
    }

}
