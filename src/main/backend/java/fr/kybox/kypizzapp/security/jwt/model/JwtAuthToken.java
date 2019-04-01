package fr.kybox.kypizzapp.security.jwt.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthToken extends UsernamePasswordAuthenticationToken {

    private String id;
    private String token;

    public JwtAuthToken(
            Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority> authorities,
            String id) {
        super(principal, credentials, authorities);
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
