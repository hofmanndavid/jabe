package io.hdavid.ui.window;

import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.GridSelectionModel;
import io.hdavid.entity.Post;
import io.hdavid.entity.Template;
import io.hdavid.entity.TemplateAsset;
import io.hdavid.util.Callback;
import io.hdavid.util.CommonWindow;
import io.hdavid.util.DUpload;
import lombok.AllArgsConstructor;
import org.vaadin.aceeditor.AceEditor;
import org.vaadin.aceeditor.AceMode;
import org.vaadin.aceeditor.AceTheme;

import java.util.Arrays;
import java.util.List;

import static net.hdavid.easylayout.L.*;

public class TemplateEditorWindow extends CommonWindow {


    Grid<Template.MustacheTemplate> mustacheTemplates = new Grid<>("Mustaches");
    Button addPartial = new Button("Add Partial");
    Button removePartial = new Button("Remove Partial");
    TextField templateName = new TextField("Template Name");
    AceEditor editor = new AceEditor(); // https://github.com/ahn/vaadin-aceeditor

    Grid<TemplateAsset> assets = new Grid<>("Assets");
    DUpload uploadAsset = new DUpload("New Asset", this::newAssetAdded);
    Button removeAsset = new Button("Remove Asset");

    final List<Template.MustacheTemplate> combinedMustacheTemplates;
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
        combinedMustacheTemplates = template.getCombinedMustacheTemplates();

        mustacheTemplates.addColumn(mt->mt.name).setCaption("code");
        mustacheTemplates.addColumn(mt->mt.partial).setCaption("partial");
        mustacheTemplates.setItems(combinedMustacheTemplates);
        mustacheTemplates.setSelectionMode(Grid.SelectionMode.SINGLE);
        mustacheTemplates.addSelectionListener(sl->{
            boolean somethingSelected = !sl.getAllSelectedItems().isEmpty();
            removePartial.setEnabled(false);
            templateName.setEnabled(somethingSelected);
            editor.setEnabled(somethingSelected);

            if (somethingSelected) {
                Template.MustacheTemplate mt = sl.getFirstSelectedItem().get();
                templateName.setEnabled(!mt.partial);
                templateName.setValue(mt.name);
                editor.setValue(mt.template);
                removePartial.setEnabled(mt.partial);
            }
        });
        addPartial.addClickListener(cl->{
            Template.MustacheTemplate nmt = new Template.MustacheTemplate(
                    "newPartial"+(combinedMustacheTemplates.size()+1),
                    true,
                    ""
            );
            combinedMustacheTemplates.add(nmt);
            mustacheTemplates.setItems(combinedMustacheTemplates);
            template.setCombinedMustacheTemplates(combinedMustacheTemplates);
            template.save();
            mustacheTemplates.select(nmt);
        });
        removePartial.setEnabled(false);
        removePartial.addClickListener(cl->{
            Template.MustacheTemplate mustacheTemplate = mustacheTemplates.getSelectedItems().stream().findFirst().get();
            combinedMustacheTemplates.remove(mustacheTemplate);
            template.setCombinedMustacheTemplates(combinedMustacheTemplates);
            template.save();
            mustacheTemplates.getSelectionModel().deselectAll();
        });
        templateName.setEnabled(false);
        templateName.setValueChangeMode(ValueChangeMode.LAZY);
        templateName.addValueChangeListener(vcl->{
            Template.MustacheTemplate mustacheTemplate = mustacheTemplates.getSelectedItems().stream().findFirst().get();
            mustacheTemplate.name = templateName.getValue();
            template.setCombinedMustacheTemplates(combinedMustacheTemplates);
            template.save();
        });
        editor.setEnabled(false);
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
        editor.addValueChangeListener(vcl->{
            Template.MustacheTemplate mustacheTemplate = mustacheTemplates.getSelectedItems().stream().findFirst().get();
            mustacheTemplate.template = vcl.getValue();
            template.setCombinedMustacheTemplates(combinedMustacheTemplates);
            template.save();
        });

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

        initAndShow("PostWindow",
                ho(_FULL_SIZE, _MARGIN,
                        ve(mustacheTemplates,
                                ho(removePartial,addPartial),
                                assets,
                                ho(removeAsset, uploadAsset)),
                        ve(_FULL_SIZE,
                                templateName,
                                editor, _FULL_SIZE,_EXPAND
                        ), _EXPAND)
        );
    }
}
