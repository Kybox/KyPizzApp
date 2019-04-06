package fr.kybox.kypizzapp.model.order;

import org.springframework.data.mongodb.core.mapping.Document;

public enum OrderStatus {

    SAVED, PENDING, PROGRESS, READY, DELIVERING, RECEIVED, CANCELLED
}
