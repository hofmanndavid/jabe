package io.hdavid.ui.window;

import com.vaadin.ui.*;
import io.hdavid.todelete.Template;
import io.hdavid.todelete.TemplateAsset;
import io.hdavid.util.CommonWindow;
import io.hdavid.util.DUpload;
import org.vaadin.aceeditor.AceEditor;
import org.vaadin.aceeditor.AceMode;
import org.vaadin.aceeditor.AceTheme;

import static net.hdavid.easylayout.L.*;

public class TemplateEditorWindow extends CommonWindow {


    Grid<Template.MustacheTemplate> mustacheTemplates = new Grid<>("Mustaches");
    Button addPartial = new Button("Add Partial");
    Button removePartial = new Button("Remove Partial");
    Button saveMustacheTemplate = new Button("Save mustache Template");
    TextField templateName = new TextField("Template Name");
    AceEditor editor = new AceEditor(); // https://github.com/ahn/vaadin-aceeditor

    Grid<TemplateAsset> assets = new Grid<>("Assets");
    DUpload uploadAsset = new DUpload("New Asset", this::newAssetAdded);
    Button removeAsset = new Button("Remove Asset");

    final Template template;
    private void newAssetAdded(String filename, String mime, byte[] content) {
        TemplateAsset ta = new TemplateAsset();
        ta.setCacheable(false);
        ta.setFile(content);
        ta.setUrl(filename);
        ta.setMimeType(mime);
        template.getAssetList().add(ta);
        template.save();
        assets.getSelectionModel().deselectAll();
        assets.setItems(template.getAssetList());
    }


    public TemplateEditorWindow(Template _template) {

        this.template = _template;

        mustacheTemplates.addColumn(mt->mt.name).setCaption("code");
        mustacheTemplates.addColumn(mt->mt.kind.name()).setCaption("Kind");
        mustacheTemplates.setItems(template.getMustacheTemplates());
        mustacheTemplates.setSelectionMode(Grid.SelectionMode.SINGLE);
        mustacheTemplates.addSelectionListener(sl->{
            boolean somethingSelected = !sl.getAllSelectedItems().isEmpty();
            if (somethingSelected) {
                Template.MustacheTemplate mt = sl.getFirstSelectedItem().get();
                templateName.setEnabled(mt.kind == Template.MustacheTemplate.Kind.PARTIAL);
                templateName.setValue(mt.name);
                editor.setValue(mt.template);
//                editor.setEnabled(true);
                removePartial.setEnabled(mt.kind == Template.MustacheTemplate.Kind.PARTIAL);
                saveMustacheTemplate.setEnabled(true);
            } else {
                removePartial.setEnabled(false);
                templateName.setEnabled(false);
                templateName.setValue("");
                editor.setValue("");
//                editor.setEnabled(false);

                saveMustacheTemplate.setEnabled(false);
            }
        });
        saveMustacheTemplate.setEnabled(false);
        saveMustacheTemplate.addClickListener(cl->{
            Template.MustacheTemplate selected = mustacheTemplates.getSelectionModel().getFirstSelectedItem().get();
            selected.name = templateName.getValue();
            selected.template = editor.getValue();
            template.markAsDirty();
            template.save();
            mustacheTemplates.setItems(template.getMustacheTemplates());
            mustacheTemplates.getSelectionModel().select(selected);
        });
        addPartial.addClickListener(cl->{
            Template.MustacheTemplate nmt = new Template.MustacheTemplate(
                    "newPartial"+(template.getMustacheTemplates().size()+1),
                    Template.MustacheTemplate.Kind.PARTIAL,
                    ""
            );
            template.getMustacheTemplates().add(nmt);
            mustacheTemplates.setItems(template.getMustacheTemplates());
            mustacheTemplates.select(nmt);
        });
        removePartial.setEnabled(false);
        removePartial.addClickListener(cl->{
            Template.MustacheTemplate mustacheTemplate = mustacheTemplates.getSelectionModel().getFirstSelectedItem().get();
            template.getMustacheTemplates().remove(mustacheTemplate);
            template.save();
            mustacheTemplates.getSelectionModel().deselectAll();
        });
        templateName.setEnabled(false);

//        editor.setEnabled(false);
        editor.setMode(AceMode.handlebars);
        editor.setTheme(AceTheme.monokai);
        editor.setUseWorker(true); // Use worker (if available for the current mode)
        editor.setThemePath("/static/ace");
        editor.setModePath("/static/ace");
        editor.setWorkerPath("/static/ace");
        editor.setWordWrap(false);
        editor.setReadOnly(false);
        editor.setShowInvisibles(false);
        editor.setFontSize("18px");

        assets.addColumn(a->a.getUrl()).setCaption("url");
        assets.addColumn(a->a.getReadableFileSize()).setCaption("size");
        assets.setItems(template.getAssetList());
        assets.addSelectionListener(sl->{
            removeAsset.setEnabled(!sl.getAllSelectedItems().isEmpty());
        });
        removeAsset.setEnabled(false);
        removeAsset.addClickListener(cl->{
            TemplateAsset taToRemove = assets.getSelectedItems().stream().findFirst().get();
            template.getAssetList().remove(taToRemove);
            taToRemove.delete();
            template.save();
            assets.setItems(template.getAssetList());
            assets.getSelectionModel().deselectAll();
        });

        mustacheTemplates.setHeight("300px");
        assets.setHeight("300px");
        initAndShow("PostWindow",
                ho(_FULL_SIZE, _MARGIN,
                        ve(mustacheTemplates,
                                ho(removePartial,addPartial),
                                assets,
                                ho(removeAsset, uploadAsset)),
                        ve(_NOOP,_FULL_SIZE,
                                ho(templateName, saveMustacheTemplate),
                                editor, _FULL_SIZE,_EXPAND
                        ), _EXPAND)
        );
    }
}
