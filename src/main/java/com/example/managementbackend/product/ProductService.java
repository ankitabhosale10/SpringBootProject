package com.example.managementbackend.product;


import java.util.List;

public interface ProductService {

    Product insertProduct(Product product);

    Product getById(Long id);

    List<Product> getproducts();

    void updateProducts( Product product);

    void deleteTodo(Long id);
}
