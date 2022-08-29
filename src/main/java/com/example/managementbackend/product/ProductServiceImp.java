package com.example.managementbackend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
//        Product product=new Product();
//        product.setProductName(pro.getProductName());
//        product.setBrand(pro.getBrand());
//        product.setDescription(pro.getDescription());
//        product.setUnitPrice(pro.getUnitPrice());
//        product.setQuantity(pro.getQuantity());
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> getProducts() {
//        List<Product> products = new ArrayList<>();
       return productRepository.findAll();

    }

    @Override
    public void updateProducts( Product product) {
//        Product product = new Product();
//        product.setProductName(pro.getProductName());
//        product.setBrand(pro.getBrand());
//        product.setDescription(pro.getDescription());
//        product.setUnitPrice(pro.getUnitPrice());
//        product.setQuantity(pro.getQuantity());
        productRepository.save(product);
    }

    @Override
    public void deleteTodo(Long id) {
        productRepository.deleteById(id);
    }
}
