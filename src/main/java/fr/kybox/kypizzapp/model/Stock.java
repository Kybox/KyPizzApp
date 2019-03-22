package fr.kybox.kypizzapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Document
public class Stock {

    @Id
    @Indexed
    private String id;

    @NotNull
    private Map<Ingredient, Integer> itemList;
}
