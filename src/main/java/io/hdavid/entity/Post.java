package io.hdavid.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
//@NoArgsConstructor
public class Post extends BasicEbeanEntity {

    private String article;
    private String url;

}
