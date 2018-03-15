package io.hdavid.entity;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.hdavid.ui.window.TemplateEditorWindow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
public class Template extends BasicEbeanEntity {


    @Column(nullable = false, unique = true)
    private String code;

    private String welcomeTemplate;
    private String postTemplate;
    private String rssTemplate;
    private String atomTemplate;
    private String postListTemplate;
    private String mustachePartialsJson;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<TemplateAsset> assetList;

    @Transient
    public Map<String, String> mustachePartials = new HashMap<>();

    public List<MustacheTemplate> getCombinedMustacheTemplates() {
        List<MustacheTemplate> mt = Arrays.asList(
                new MustacheTemplate("welcome", false, welcomeTemplate),
                new MustacheTemplate("atom", false, atomTemplate),
                new MustacheTemplate("post", false, postTemplate),
                new MustacheTemplate("rss", false, rssTemplate),
                new MustacheTemplate("postList", false, postListTemplate));
        mustachePartials.forEach((code, pt) -> mt.add(new MustacheTemplate(code, true, pt)));
        return mt;
    }
    public void setCombinedMustacheTemplates(List<MustacheTemplate> mt) {
        welcomeTemplate = mt.stream().filter(m->m.name.equals("welcome")).findFirst().get().template;
        atomTemplate = mt.stream().filter(m->m.name.equals("atom")).findFirst().get().template;
        postTemplate = mt.stream().filter(m->m.name.equals("post")).findFirst().get().template;
        rssTemplate = mt.stream().filter(m->m.name.equals("rss")).findFirst().get().template;
        postListTemplate = mt.stream().filter(m->m.name.equals("postList")).findFirst().get().template;

        mustachePartials.clear();
        mt.stream().filter(m->m.partial).forEach((m)->mustachePartials.put(m.name, m.template));
    }

    @PrePersist
    @PreUpdate
    private void syncToDb() {
        setMustachePartialsJson(new GsonBuilder().disableHtmlEscaping().create().toJson(mustachePartials));
    }

    @PostLoad
    private void syncFromDb() {
        if (mustachePartialsJson!= null && mustachePartialsJson.isEmpty()) {
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            mustachePartials = new GsonBuilder().disableHtmlEscaping().create().fromJson(mustachePartialsJson, type);
        }
    }

    @AllArgsConstructor
    public static class MustacheTemplate {
        public String name;
        public boolean partial;
        public String template;

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return name.equals(obj);
        }
    }
}
