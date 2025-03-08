package com.example.demo.login;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
public class PrincipalDetails implements UserDetails {
    private final Login login;
    public PrincipalDetails(Login login) {
        this.login = login;
    }

    @Override
    public Collection<? extends
            GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword(){
        return login.getPassword();
    }

    @Override
    public String getUsername(){
        return login.getEmail();
    }
}
