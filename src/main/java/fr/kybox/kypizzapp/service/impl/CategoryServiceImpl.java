package fr.kybox.kypizzapp.service.impl;

import fr.kybox.kypizzapp.model.Category;
import fr.kybox.kypizzapp.repository.CategoryRepository;
import fr.kybox.kypizzapp.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAllCategories() {

        return categoryRepository.findAll();
    }

    @Override
    public Category addNewCategory(Category category) {

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {

        Optional<Category> optCategory = categoryRepository.findById(category.getId());
        if(!optCategory.isPresent()) throw new RuntimeException("Not found");

        return categoryRepository.save(category);
    }

    @Override
    public Boolean deleteCategory(String id) {

        categoryRepository.deleteById(id);
        return !categoryRepository.existsById(id);
    }
}
