package fr.kybox.kypizzapp.service.impl;

import fr.kybox.kypizzapp.model.Category;
import fr.kybox.kypizzapp.repository.CategoryRepository;
import fr.kybox.kypizzapp.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

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
    public ResponseEntity updateCategory(Category category) {

        log.info("Update category");

        Optional<Category> optCategory = categoryRepository.findById(category.getId());

        if(!optCategory.isPresent())
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @Override
    public Boolean deleteCategory(String id) {

        categoryRepository.deleteById(id);
        return !categoryRepository.existsById(id);
    }

    @Override
    public Optional<Category> findById(String id) {

        return categoryRepository.findById(id);
    }
}
