package com.example.managementbackend.product;


import java.util.List;

public interface ProductService {

    Product insertProduct(Product product);

    Product getById(Long id);

    List<Product> getProducts();

    void updateProductsById(Long id, Product product);

    void deleteProducts(Long id);
}
