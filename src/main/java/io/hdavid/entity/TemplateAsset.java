package io.hdavid.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemplateAsset extends BasicEbeanEntity {

    private String url;
    private String mimeType;

    private boolean cacheable;

    @Column(columnDefinition = "BINARY(10485760)") // 10 MB
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
