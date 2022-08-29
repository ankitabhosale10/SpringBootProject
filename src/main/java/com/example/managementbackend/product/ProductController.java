package com.example.managementbackend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

//    @PostMapping("/api/productEntry")
//    public ModelAndView productEntry(@ModelAttribute Product product){
//        ModelAndView mv = new ModelAndView();
//        productService.insertProduct(product);
//        mv.setViewName("product_success");
//        return mv;
//    }

    @PostMapping("/api/productEntry")
    public ResponseEntity<Product> productEntry(@RequestBody Product product){
        productService.insertProduct(product);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("product", "/product" + product1.getId().toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/getById")
    public ResponseEntity getById(@RequestParam(value ="id") Long Id) {
        return new ResponseEntity<>(productService.getById(Id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/api/updateProduct")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        productService.updateProducts(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/deleteProduct")
    public ResponseEntity<Product> deleteProduct(@RequestParam(value ="id") Long Id) {
        productService.deleteTodo(Id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
