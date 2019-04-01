package fr.kybox.kypizzapp.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kypizzapp.cookie.cart")
public class CookieProperties {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
