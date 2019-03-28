package fr.kybox.kypizzapp.model;

import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;

@Document
public class Order {

    @Id
    private String id;

    @DBRef
    private Restaurant restaurant;

    @DBRef
    private RegisteredUser customer;

    @NotNull
    private OrderStatus status;

    @NotNull
    private Map<Product, Integer> productList;

    @NotNull
    private Boolean paid;

    @NotNull
    private Boolean toDeliver;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public RegisteredUser getCustomer() {
        return customer;
    }

    public void setCustomer(RegisteredUser customer) {
        this.customer = customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Map<Product, Integer> getProductList() {
        return productList;
    }

    public void setProductList(Map<Product, Integer> productList) {
        this.productList = productList;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Boolean getToDeliver() {
        return toDeliver;
    }

    public void setToDeliver(Boolean toDeliver) {
        this.toDeliver = toDeliver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getId().equals(order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
