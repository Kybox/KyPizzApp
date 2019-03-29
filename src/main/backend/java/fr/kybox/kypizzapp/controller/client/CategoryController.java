package fr.kybox.kypizzapp.controller.client;

import fr.kybox.kypizzapp.model.Category;
import fr.kybox.kypizzapp.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class CategoryController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Category>> getAllCategories(){

        log.info("Get all categories");
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @GetMapping(value = "/category/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Category> findCategoryById(@PathVariable String id){

        log.info("Get category by id : " + id);
        return ResponseEntity.of(categoryService.findById(id));
    }
}
