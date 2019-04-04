package fr.kybox.kypizzapp.model.cart;

import java.util.List;

public class Cart {

    private String userId;
    private List<CartProduct> productList;

    public Cart() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<CartProduct> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId='" + userId + '\'' +
                ", productList=" + productList +
                '}';
    }
}
