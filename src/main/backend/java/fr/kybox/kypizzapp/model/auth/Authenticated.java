package fr.kybox.kypizzapp.model.auth;

public class Authenticated {

    private boolean authenticated;

    public Authenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
