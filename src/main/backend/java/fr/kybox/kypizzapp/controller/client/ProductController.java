package fr.kybox.kypizzapp.controller.client;

import fr.kybox.kypizzapp.model.Category;
import fr.kybox.kypizzapp.model.Product;
import fr.kybox.kypizzapp.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/client")
public class ProductController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/products/by/category",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Product>> getProductListByCategory(@RequestBody @Valid Category category) {

        log.info("Get product list by category : " + category.getName());

        return ResponseEntity.ok(productService.findProductsByCategory(category));
    }
}
