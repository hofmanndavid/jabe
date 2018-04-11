package io.hdavid.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
public class Post extends BasicEbeanEntity {

    private static final Parser parser = Parser.builder().build();

    @Column(nullable = false)
    public String title;

    @Column(nullable = false)
    private String markdown;

    @Column(nullable = false)
    private String url;

    public boolean draft;

    /** Auto Updated Fields */
    @Column(nullable = false)
    private String renderedMarkdown;

    private Long firstPublishedOnMillis;

    @Column(nullable = false)
    private Long lastUpdatedMillis;

    @PrePersist @PreUpdate
    public void preSave() {
        Node document = parser.parse(getMarkdown());
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        setRenderedMarkdown(renderer.render(document));
        setLastUpdatedMillis(System.currentTimeMillis());
        if (getFirstPublishedOnMillis() == null && !draft)
            setFirstPublishedOnMillis(getLastUpdatedMillis());
    }
}
