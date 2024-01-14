package com.fii.backendapp.util;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUser extends User implements UserDetails {
    @Getter
    private final Long uid;
    private final Boolean isProfessor;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long uid,
                      Boolean isProfessor) {
        super(username, password, authorities);
        this.uid = uid;
        this.isProfessor = isProfessor;
    }

    public Boolean isProfessor() {
        return isProfessor;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
