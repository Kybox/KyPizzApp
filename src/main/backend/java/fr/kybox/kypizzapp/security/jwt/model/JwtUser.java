package fr.kybox.kypizzapp.security.jwt.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class JwtUser extends User {

    private String id;
    private boolean active;

    public JwtUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String id, boolean active) {
        super(username, password, authorities);
        this.id = id;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
