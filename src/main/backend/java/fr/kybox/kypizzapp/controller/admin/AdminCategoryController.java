package fr.kybox.kypizzapp.controller.admin;

import fr.kybox.kypizzapp.model.Category;
import fr.kybox.kypizzapp.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/api")
public class AdminCategoryController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/category", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Category> addNewCategory(@RequestBody @Valid Category category) {

        log.info("Add new category");
        return ResponseEntity.ok(categoryService.addNewCategory(category));
    }

    @PutMapping(value = "/category", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateCategory(@RequestBody @Valid Category category) {

        log.info("Update category");
        return categoryService.updateCategory(category);
    }

    @DeleteMapping(value = "/category/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable String id) {

        log.info("Delete category " + id);
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}
