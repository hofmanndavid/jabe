package io.hdavid.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter

public class User extends BasicEbeanEntity {

    private String username;
    private String displayName;
    private String password;

}
