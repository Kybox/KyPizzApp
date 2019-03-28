package fr.kybox.kypizzapp.repository;

import fr.kybox.kypizzapp.model.Category;
import fr.kybox.kypizzapp.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findAllByCategory(Category category);
}
