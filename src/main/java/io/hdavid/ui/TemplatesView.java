package io.hdavid.ui;

import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.hdavid.entity.Template;
import io.hdavid.entity.query.QTemplate;
import io.hdavid.ui.window.TemplateEditorWindow;

import static net.hdavid.easylayout.L.*;

public class TemplatesView extends VerticalLayout implements View {

    public static final String NAME = "templatesView";

    Grid<Template> grid = new Grid<>();
    Button newB = new Button("New");
    Button editB = new Button("Modify");
//    Button deleteB = new Button("Delete");

    public TemplatesView() {
        grid.addColumn(t->t.getCode()).setCaption("Codigo");

        grid.addSelectionListener(sl -> {
            boolean selected = sl.getFirstSelectedItem().isPresent();

            editB.setEnabled(selected);
//            deleteB.setEnabled(selected);

        });
        editB.setEnabled(false);
//        deleteB.setEnabled(false);

        editB.addClickListener(cl->{
            TemplateEditorWindow tw = new TemplateEditorWindow(grid.getSelectedItems().iterator().next());
            tw.addCloseListener(wcl->loadGrid());
        });
        newB.addClickListener(cl->{
            Template newTemplate = new Template();
            TemplateEditorWindow tw = new TemplateEditorWindow(newTemplate);
            tw.addCloseListener(wcl->loadGrid());
        });

        loadGrid();

        ve(this, _FULL_SIZE, _MARGIN,
                ho(editB, newB, ValoTheme.BUTTON_PRIMARY), Alignment.TOP_RIGHT,
                grid, _FULL_SIZE, _EXPAND);
    }

    void loadGrid() {
        grid.setItems(new QTemplate().id.asc().findList());
    }

}
