package fr.kybox.kypizzapp.model;

public class GenericObject {

    private String data;

    public GenericObject() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GenericObject{" +
                "data='" + data + '\'' +
                '}';
    }
}
