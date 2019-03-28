package fr.kybox.kypizzapp.controller.admin;

import fr.kybox.kypizzapp.model.Image;
import fr.kybox.kypizzapp.model.Product;
import fr.kybox.kypizzapp.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminProductController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Product>> getProductList(){

        return ResponseEntity.ok(productService.getProductList());
    }

    @PostMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Product> addNewProduct(@RequestBody @Valid Product product){

        return ResponseEntity.ok(productService.addNewProduct(product));
    }

    @PostMapping(value = "/product/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image> addnewFile(@RequestParam("file")MultipartFile file){

        log.info("obj = " + file);

        return ResponseEntity.ok(productService.addNewImage(file));
    }

    @PutMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product product){

        log.info("Update product");
        return ResponseEntity.ok(productService.updateProduct(product));
    }
}
