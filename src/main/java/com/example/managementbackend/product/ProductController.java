package com.example.managementbackend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        Product product1 = productService.insertProduct(product);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("product", "/product" + product1.getId().toString());
        return new ResponseEntity<>(product1, httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/api/getById")
    public ResponseEntity getById(@RequestParam(value ="id") Long Id) {
        return new ResponseEntity<>(productService.getById(Id), HttpStatus.OK);
    }

}
