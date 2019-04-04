package fr.kybox.kypizzapp.service;

import fr.kybox.kypizzapp.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAllCategories();

    Category addNewCategory(Category category);

    ResponseEntity updateCategory(Category category);

    Boolean deleteCategory(String id);

    Optional<Category> findById(String id);
}
