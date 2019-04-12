package fr.kybox.kypizzapp.service;

import fr.kybox.kypizzapp.exception.NotFoundException;
import fr.kybox.kypizzapp.model.Category;
import fr.kybox.kypizzapp.model.Image;
import fr.kybox.kypizzapp.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Product addNewProduct(Product product);

    Image addNewImage(MultipartFile file);

    List<Product> getProductList();

    List<Product> findProductsByCategory(Category category);

    Product updateProduct(Product product);

    Product findProductById(String id) throws NotFoundException;
}
