package fr.kybox.kypizzapp.model.cart;

public class CartProduct {

    private String id;
    private int quantity;

    public CartProduct() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
                "id='" + id + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
