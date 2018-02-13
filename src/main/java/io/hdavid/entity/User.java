package io.hdavid.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter

@NoArgsConstructor
public class User extends BasicEbeanEntity {

    public User(String username) {
        this.username = username;
    }

    private String username;
    private String displayName;
    private String password;

}
