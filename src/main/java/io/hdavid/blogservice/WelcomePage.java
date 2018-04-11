package io.hdavid.blogservice;

import io.hdavid.entity.Post;
import io.hdavid.entity.Template;
import io.hdavid.entity.query.QPost;
import io.hdavid.entity.query.QTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class WelcomePage implements BlogPage {

//    static class

    public WelcomePage() {
        Template.MustacheTemplate welcomeTemplate =
                new QTemplate().code.eq("base").findOne()
                        .getMustacheTemplates()
                        .stream()
                        .filter(t->t.kind == Template.MustacheTemplate.Kind.WELCOME)
                        .findFirst().get();
        List<Post> postList = new QPost().draft.isFalse().setMaxRows(10).id.desc().findList();



    }

    @Override
    public void render(HttpServletResponse resp) {

    }
}
