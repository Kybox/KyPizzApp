package fr.kybox.kypizzapp.model;

import fr.kybox.kypizzapp.model.auth.Address;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.Objects;

@Document
public class Restaurant {

    @Id
    private String id;

    @Indexed
    private String name;

    @DBRef
    private Address address;

    private String tel;
    private String email;

    private Map<String, String> openingTime;

    @DBRef
    private RegisteredUser manager;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, String> getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Map<String, String> openingTime) {
        this.openingTime = openingTime;
    }

    public RegisteredUser getManager() {
        return manager;
    }

    public void setManager(RegisteredUser manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant)) return false;
        Restaurant that = (Restaurant) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
