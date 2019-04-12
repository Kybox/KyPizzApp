package fr.kybox.kypizzapp.model.payment.paypal;

import java.util.Map;

public class PaypalPayUrl {

    private String payUrl;
    private Map<String, String> headers;

    public PaypalPayUrl() {
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "PaypalPayUrl{" +
                "payUrl='" + payUrl + '\'' +
                ", headers=" + headers +
                '}';
    }
}
