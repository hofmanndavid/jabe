package io.hdavid.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@Getter
@Setter
public class TemplateAsset extends BasicEbeanEntity {

    private String url;
    private String mimeType;

    private boolean cacheable;
    private byte[] file;

    @Transient
    public String getReadableFileSize() {
        if (file == null)
            return "0";
        if (file.length < 1024)
            return file.length + " bytes";
        if (file.length < (1024*1024))
            return (file.length/1024) + " Kb";

        return ((file.length/1024)/1024) + " Mb";
    }

}
