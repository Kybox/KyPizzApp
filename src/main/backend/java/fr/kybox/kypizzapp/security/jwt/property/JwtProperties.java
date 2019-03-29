package fr.kybox.kypizzapp.security.jwt.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kypizzapp.security.jwt")
public class JwtProperties {

    private String header;
    private String prefix;
    private String signingKey;
    private long expiration;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
