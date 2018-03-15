package io.hdavid.crosscut;

import io.hdavid.entity.Template;
import io.hdavid.entity.TemplateAsset;
import io.hdavid.entity.User;
import io.hdavid.entity.query.QTemplate;
import io.hdavid.entity.query.QUser;
import io.hdavid.util.JarResources;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InitData {

    public static void init() {

        if (new QUser().username.eq("admin").findOne() == null) {
            User usuario = new User();
            usuario.setUsername("admin");
            usuario.setPassword("admin");
            usuario.save();
        }
        Template ttr = new QTemplate().code.eq("base").findOne();
        if (ttr != null)
            ttr.delete();
        if (new QTemplate().code.eq("base").findOne() == null) {
            Template template = new Template();
            template.setCode("base");
            Map<String, String> mr = (Map) JarResources.getFileResourcesInPath("/basetemplate/", false, ".mustache");
            template.setWelcomeTemplate (mr.remove("welcome.mustache"));
            template.setAtomTemplate    (mr.remove("atom.mustache"));
            template.setRssTemplate     (mr.remove("rss.mustache"));
            template.setPostTemplate    (mr.remove("post.mustache"));
            template.setPostListTemplate(mr.remove("postlist.mustache"));

            Map<String, String> partials = new HashMap<>();
            mr.forEach((name, content)-> partials.put(name.replace(".mustache",""), content) );
            template.setMustachePartials(partials);

            Map<String, byte[]> al = (Map) JarResources.getFileResourcesInPath("/basetemplate/", true,
                    ".png", ".jpg");

            al.forEach((name, content) -> {
                TemplateAsset ta = new TemplateAsset();
                ta.setUrl(name);
                ta.setCacheable(false);
//                ta.setMimeType("");// ?
                ta.setFile(content);
            });

            template.save();
        }
    }
}
