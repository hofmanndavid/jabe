package io.hdavid.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Post extends BasicEbeanEntity {

    @Column(nullable = false)
    public String title;

    @Column(nullable = false)
    private String markdownArticle;

    @Column(nullable = false)
    private String url;

    private Timestamp publishedOn;

    @Column(nullable = false)
    private Timestamp lastUpdated;

}
