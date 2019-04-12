package fr.kybox.kypizzapp.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import fr.kybox.kypizzapp.exception.NotFoundException;
import fr.kybox.kypizzapp.exception.ProductNotFoundException;
import fr.kybox.kypizzapp.model.Category;
import fr.kybox.kypizzapp.model.Image;
import fr.kybox.kypizzapp.model.Product;
import fr.kybox.kypizzapp.repository.ProductRepository;
import fr.kybox.kypizzapp.service.ProductService;
import fr.kybox.kypizzapp.utils.file.FileExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private String imgFolder;

    private GridFSDBFile gridFSDBFile;
    private final GridFsOperations gridFsOperations;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              @Value("${app.storage}") String imgFolder,
                              GridFsOperations gridFsOperations) {

        this.productRepository = productRepository;
        this.imgFolder = imgFolder;
        this.gridFsOperations = gridFsOperations;
    }

    @Override
    public Product addNewProduct(Product product) {

        return productRepository.insert(product);
    }

    @Override
    public Image addNewImage(MultipartFile image) {

        String fileExtension = FileExtension.get(image.getOriginalFilename()).get();
        String fileName = UUID.randomUUID().toString() + fileExtension;

        DBObject metaData = new BasicDBObject();
        metaData.put("type", "image");

        try{
            gridFsOperations.store(
                    image.getInputStream(),
                    fileName,
                    "image/png", metaData);
        }
        catch (IOException e) { e.printStackTrace(); }

        return new Image(fileName);
    }

    @Override
    public List<Product> getProductList() {

        return productRepository.findAll();
    }

    @Override
    public List<Product> findProductsByCategory(Category category) {

        return productRepository.findAllByCategory(category);
    }

    @Override
    public Product updateProduct(Product product) {

        Optional<Product> optProduct = productRepository.findById(product.getId());
        if(!optProduct.isPresent()) throw new RuntimeException("Not found");

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(String id) throws NotFoundException {

        Optional<Product> optProduct = productRepository.findById(id);

        return optProduct.orElseThrow(() -> new NotFoundException("The product was not found."));
    }
}
