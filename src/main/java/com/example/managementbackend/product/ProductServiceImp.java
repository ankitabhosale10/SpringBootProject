package com.example.managementbackend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ProductServiceImp implements ProductService{


    @Autowired
    private ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product insertProduct(Product pro) {
        Product product=new Product();
        product.setProductName(pro.getProductName());
        product.setBrand(pro.getBrand());
        product.setDescription(pro.getDescription());
        product.setUnitPrice(pro.getUnitPrice());
        product.setQuantity(pro.getQuantity());
        pro.setActive(false);
        return productRepository.save(pro);
    }
}
