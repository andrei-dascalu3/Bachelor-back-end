package com.fii.backendapp.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUser extends User implements UserDetails {
    private final Long uid;
    private final Boolean isProfessor;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long uid,
                      Boolean isProfessor) {
        super(username, password, authorities);
        this.uid = uid;
        this.isProfessor = isProfessor;
    }

    public Long getUid() {
        return uid;
    }

    public Boolean isProfessor() {
        return isProfessor;
    }
}
