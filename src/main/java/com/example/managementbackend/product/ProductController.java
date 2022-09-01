package com.example.managementbackend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
       Product pro= productService.insertProduct(product);
        return new ResponseEntity<>(pro,HttpStatus.OK);
    }

    @GetMapping("/api/getById/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id ) {
        return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/api/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id,@RequestBody Product product) {
        productService.updateProductsById(id,product);
        return new ResponseEntity<>(productService.getById(id),HttpStatus.OK);
    }

    @DeleteMapping("/api/deleteProduct")
    public ResponseEntity<Product> deleteProduct(@RequestParam(value ="id") Long Id) {
        productService.deleteProducts(Id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
