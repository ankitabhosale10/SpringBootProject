package com.example.managementbackend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProductServiceImp implements ProductService{


    @Autowired
    private ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product insertProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> getProducts() {
       return productRepository.findAll();
    }

    @Override
    public void updateProducts( Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProducts(Long id) {
        productRepository.deleteById(id);
    }
}
