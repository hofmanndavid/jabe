package io.hdavid.ui;

import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.hdavid.entity.Post;
import io.hdavid.entity.query.QPost;
import io.hdavid.ui.window.PostEditorWindow;

import static net.hdavid.easylayout.L.*;

public class PostsView extends VerticalLayout implements View {

    public static final String NAME = "posts";

    Grid<Post> grid = new Grid<>(Post.class);

    Button newPost = new Button("New Post");
    Button modifyPost = new Button("Modify");
    Button deleteButton = new Button("Delete");

    public PostsView() {
        grid.addSelectionListener(sl->{
            boolean somethingSelected = sl.getFirstSelectedItem().isPresent();
            modifyPost.setEnabled(somethingSelected);
            deleteButton.setEnabled(somethingSelected);
        });

        newPost.addClickListener(cl->{
            Post post = new Post();
            new PostEditorWindow(post, ()->{
                loadPosts();
                grid.getSelectionModel().select(post);
            });
        });

        modifyPost.addClickListener(cl->{
            new PostEditorWindow(grid.getSelectionModel().getFirstSelectedItem().get(), ()->loadPosts());
        });
        deleteButton.addClickListener(cl->{
           grid.getSelectionModel().getFirstSelectedItem().get().delete();
           loadPosts();
           grid.getSelectionModel().deselectAll();
        });
        loadPosts();
        ve(this,_FULL_SIZE,_MARGIN,
                ho(deleteButton, ValoTheme.BUTTON_DANGER, modifyPost, newPost, ValoTheme.BUTTON_PRIMARY), Alignment.MIDDLE_RIGHT,
                grid, _FULL_SIZE, _EXPAND);
    }

    void loadPosts() {
        grid.setItems(new QPost().findList());

    }
}
