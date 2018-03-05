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

    public PostsView() {
        newPost.addClickListener(cl->{
            Post post = new Post();
            new PostEditorWindow(post, ()->loadPosts());
        });

        modifyPost.addClickListener(cl->{
            new PostEditorWindow(grid.getSelectionModel().getFirstSelectedItem().get(), ()->loadPosts());
        });
        ve(this,_FULL_SIZE,_MARGIN,
                ho(modifyPost, newPost, ValoTheme.BUTTON_PRIMARY), Alignment.MIDDLE_RIGHT,
                grid, _FULL_SIZE, _EXPAND);
    }

    void loadPosts() {
        grid.setItems(new QPost().findList());

    }
}
