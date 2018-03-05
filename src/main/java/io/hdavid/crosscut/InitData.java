package io.hdavid.crosscut;

import io.hdavid.entity.User;
import io.hdavid.entity.query.QUser;

public class InitData {

    public static void init() {
        User usuario = new QUser().username.eq("admin").findOne();
        if (usuario == null) {
            usuario = new User();
            usuario.setUsername("admin");
            usuario.setPassword("admin");
            usuario.save();
        }
    }
}
