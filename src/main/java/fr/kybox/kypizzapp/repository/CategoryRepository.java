package fr.kybox.kypizzapp.repository;

import fr.kybox.kypizzapp.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
