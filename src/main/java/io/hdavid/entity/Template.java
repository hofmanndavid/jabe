package io.hdavid.entity;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.hdavid.ui.window.TemplateEditorWindow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
public class Template extends BasicEbeanEntity {

    @AllArgsConstructor
    public static class MustacheTemplate {
        public enum Kind {
            WELCOME,POST,RSS,ATOM,POST_LIST,PARTIAL
        }
        public MustacheTemplate(Kind kind, String template) {
            this(kind.name(), kind, template);
        }
        public String name;
        public Kind kind;
        public String template;
        @Override public int hashCode() {
            return name.hashCode();
        }
        @Override public boolean equals(Object obj) {
            return name.equals(obj);
        }
    }

    @Column(nullable = false, unique = true)
    private String code;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<TemplateAsset> assetList;

    @Column(length = 1024*1024)
    private String jsonMustacheTemplates;

    @Transient
    private List<MustacheTemplate> _mustacheTemplates;
    @Transient
    public List<MustacheTemplate> getMustacheTemplates() {
        if (_mustacheTemplates == null)
            _mustacheTemplates = new MustacheTemplateListConverter().convertToEntityAttribute(jsonMustacheTemplates);
        return _mustacheTemplates;
    }

    @PrePersist @PreUpdate
    public void _syncToDb() {
        setJsonMustacheTemplates(new MustacheTemplateListConverter().convertToDatabaseColumn(_mustacheTemplates));
    }

}
