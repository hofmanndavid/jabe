package io.hdavid.ui.window;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import io.hdavid.entity.Post;
import io.hdavid.util.Callback;
import io.hdavid.util.CommonWindow;
import org.vaadin.aceeditor.AceEditor;
import org.vaadin.aceeditor.AceMode;
import org.vaadin.aceeditor.AceTheme;
import org.vaadin.aceeditor.client.AceEditorWidget;

import static net.hdavid.easylayout.L.*;

public class PostEditorWindow extends CommonWindow {

    AceEditor editor = new AceEditor(); // https://github.com/ahn/vaadin-aceeditor
    Button aceptar = new Button("Guardar");
    Button cancelar = new Button("Descartar");

    public PostEditorWindow(Post post, Callback callback) {


        editor.setValue(post.getMarkdownArticle());
        editor.setMode(AceMode.markdown);
        editor.setTheme(AceTheme.cobalt);

        editor.setUseWorker(true); // Use worker (if available for the current mode)

        editor.setThemePath("/static/ace");
        editor.setModePath("/static/ace");
        editor.setWorkerPath("/static/ace");

        editor.setWordWrap(false);
        editor.setReadOnly(false);
        editor.setShowInvisibles(false);

        aceptar.addClickListener(cl->{
            post.setMarkdownArticle(editor.getValue());
            callback.call();
        });

        editor.addValueChangeListener(vcl -> {
            System.out.println("new text: "+ vcl.getValue()+"\n\n");
        });

        initAndShow("PostWindow", ve(_FULL_SIZE, _MARGIN,
                editor, _FULL_SIZE, _EXPAND,
                ho(cancelar, aceptar), Alignment.BOTTOM_RIGHT));
    }
}
