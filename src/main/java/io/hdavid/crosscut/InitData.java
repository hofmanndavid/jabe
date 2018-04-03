package io.hdavid.crosscut;

import io.hdavid.entity.Template;
import io.hdavid.entity.TemplateAsset;
import io.hdavid.entity.User;
import io.hdavid.entity.query.QTemplate;
import io.hdavid.entity.query.QUser;
import io.hdavid.util.JarResources;

import java.util.HashMap;
import java.util.List;
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
//        Template ttr = new QTemplate().code.eq("base").findOne();
//        if (ttr != null)
//            ttr.delete();
        if (new QTemplate().code.eq("base").findOne() == null) {
            Template template = new Template();
            template.setCode("base");
            Map<String, String> mr = (Map) JarResources.getFileResourcesInPath("/basetemplate/", false, ".mustache");
            template.getMustacheTemplates().add(new Template.MustacheTemplate(Template.MustacheTemplate.Kind.WELCOME,mr.remove("welcome.mustache")));
            template.getMustacheTemplates().add(new Template.MustacheTemplate(Template.MustacheTemplate.Kind.ATOM,mr.remove("atom.mustache")));
            template.getMustacheTemplates().add(new Template.MustacheTemplate(Template.MustacheTemplate.Kind.RSS,mr.remove("rss.mustache")));
            template.getMustacheTemplates().add(new Template.MustacheTemplate(Template.MustacheTemplate.Kind.POST,mr.remove("post.mustache")));
            template.getMustacheTemplates().add(new Template.MustacheTemplate(Template.MustacheTemplate.Kind.POST_LIST,mr.remove("postlist.mustache")));

            mr.forEach((name, content)->
                template.getMustacheTemplates().add(
                        new Template.MustacheTemplate(
                                name.replace(".mustache",""),
                                Template.MustacheTemplate.Kind.PARTIAL,
                                content
                        )
                )
            );

            Map<String, byte[]> al = (Map) JarResources.getFileResourcesInPath("/basetemplate/", true,
                    ".png", ".jpg");


            List<TemplateAsset> talist = al.entrySet().stream()
                    .map(e ->
                            new TemplateAsset(e.getKey(),null,false,e.getValue())
                    ).collect(Collectors.toList());

            template.setAssetList(talist);

            template.save();
        }
    }
}
