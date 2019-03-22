package fr.kybox.kypizzapp.model;

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
