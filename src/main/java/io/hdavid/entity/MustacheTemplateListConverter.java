package io.hdavid.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;

@Converter
public class MustacheTemplateListConverter implements AttributeConverter<List<Template.MustacheTemplate>, String> {

    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    @Override
    public String convertToDatabaseColumn(List<Template.MustacheTemplate> attribute) {
        return gson.toJson(attribute);
    }

    @Override
    public List<Template.MustacheTemplate> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty())
            return new ArrayList<>();
        return gson.fromJson(dbData, new TypeToken<List<Template.MustacheTemplate>>() {}.getType());
    }
}
