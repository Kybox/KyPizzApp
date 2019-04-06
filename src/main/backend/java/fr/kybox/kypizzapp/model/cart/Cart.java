package fr.kybox.kypizzapp.model.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<ProductFromCart> productList;

    public Cart() {
    }

    public List<ProductFromCart> getProductList() {

        if(productList == null) productList = new ArrayList<>();
        return productList;
    }

    public void setProductList(List<ProductFromCart> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "productList=" + productList +
                '}';
    }
}
