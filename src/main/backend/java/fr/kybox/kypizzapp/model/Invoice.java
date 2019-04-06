package fr.kybox.kypizzapp.model;

import fr.kybox.kypizzapp.model.order.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Document
public class Invoice {

    @Id
    private String id;

    @NotNull
    private String ref;

    private LocalDateTime date;

    @DBRef
    @NotNull
    private Order order;
}
