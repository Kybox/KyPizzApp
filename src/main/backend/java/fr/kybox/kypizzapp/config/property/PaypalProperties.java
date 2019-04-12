package fr.kybox.kypizzapp.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kypizzapp.payment.api.paypal")
public class PaypalProperties {

    private String clientId;
    private String secret;
    private String sandboxUrl;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSandboxUrl() {
        return sandboxUrl;
    }

    public void setSandboxUrl(String sandboxUrl) {
        this.sandboxUrl = sandboxUrl;
    }
}
