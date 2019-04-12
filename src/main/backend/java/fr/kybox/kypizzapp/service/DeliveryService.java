package fr.kybox.kypizzapp.service;

import fr.kybox.kypizzapp.model.order.DeliveryMethod;

import java.util.List;

public interface DeliveryService {

    DeliveryMethod create(DeliveryMethod deliveryMethod);
    DeliveryMethod update(DeliveryMethod deliveryMethod);
    List<DeliveryMethod> findAll();
    DeliveryMethod findById(String id);
    List<DeliveryMethod> delete(String id);
}
