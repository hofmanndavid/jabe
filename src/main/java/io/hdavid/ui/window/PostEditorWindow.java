package io.hdavid.ui.window;

import com.vaadin.ui.*;
import io.hdavid.entity.Post;
import io.hdavid.util.Callback;
import io.hdavid.util.CommonWindow;
import org.vaadin.aceeditor.AceEditor;
import org.vaadin.aceeditor.AceMode;
import org.vaadin.aceeditor.AceTheme;
import org.vaadin.aceeditor.client.AceEditorWidget;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static net.hdavid.easylayout.L.*;

public class PostEditorWindow extends CommonWindow {

    AceEditor editor = new AceEditor(); // https://github.com/ahn/vaadin-aceeditor
    CheckBox preppendYearAndMonth = new CheckBox("Preppend year/month to your url?", true);
    TextField url = new TextField("Url", "");
    TextField title = new TextField("Title", "");
    CheckBox draft  = new CheckBox("Draft?", true);

    Button aceptar = new Button("Guardar");
    Button cancelar = new Button("Descartar");

    public PostEditorWindow(Post post, Callback callback) {
        if (post.isPersisted()) {
            editor.setValue(post.getMarkdown());
            draft.setValue(post.isDraft());
            preppendYearAndMonth.setEnabled(false);
            url.setValue(post.getUrl());
            title.setValue(post.getTitle());
        }

        editor.setValue(post.getMarkdown());
        editor.setMode(AceMode.markdown);
        editor.setTheme(AceTheme.cobalt);

        editor.setUseWorker(true); // Use worker (if available for the current mode)

        editor.setThemePath("/static/ace");
        editor.setModePath("/static/ace");
        editor.setWorkerPath("/static/ace");

        editor.setWordWrap(false);
        editor.setReadOnly(false);
        editor.setShowInvisibles(false);
        editor.setFontSize("19px");

        aceptar.addClickListener(cl->{
            post.setMarkdown(editor.getValue());
            post.setDraft(draft.getValue());
            String preppendedDate = preppendYearAndMonth.getValue() ?
                    new SimpleDateFormat("yyyy/MM/").format(new Date()) : "";
            post.setUrl(preppendedDate + url.getValue());
            post.setTitle(title.getValue());
            post.save();
            callback.call();
            close();
        });
        cancelar.addClickListener(cl->{
            close();
        });

        editor.addValueChangeListener(vcl -> {
            System.out.println("new text: "+ vcl.getValue()+"\n\n");
        });

        initAndShow("PostWindow", ve(_FULL_SIZE, _MARGIN,
                ho(preppendYearAndMonth, url, title, draft),
                editor, _FULL_SIZE, _EXPAND,
                ho(cancelar, aceptar), Alignment.BOTTOM_RIGHT));
    }
}
