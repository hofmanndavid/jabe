package io.hdavid.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Post extends BasicEbeanEntity {

    @Column(nullable = false)
    public String title;

    @Column(nullable = false)
    private String markdown;

    @Column(nullable = false)
    private String url;

    private boolean published;

    private String markdownTemplateCode;

    private Long firstPublishedOnMillis;

    @Column(nullable = false)
    private Long lastUpdatedMillis;

}
