package fr.kybox.kypizzapp.service;

import fr.kybox.kypizzapp.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategories();

    Category addNewCategory(Category category);

    Category updateCategory(Category category);

    Boolean deleteCategory(String id);
}
