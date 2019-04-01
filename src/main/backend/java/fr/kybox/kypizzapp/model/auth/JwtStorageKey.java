package fr.kybox.kypizzapp.model.auth;

public class JwtStorageKey {

    private String key;

    public JwtStorageKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
