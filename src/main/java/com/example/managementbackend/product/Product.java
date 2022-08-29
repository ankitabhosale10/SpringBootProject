package com.example.managementbackend.product;


import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "productDetails")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "brand")
    @NotBlank(message = "Please Enter the Brand Name")
    private String brand;

    @Column(name = "description")
    @NotBlank(message = "Please Enter the Product Description")
    private String description;

    @Column(name = "unit_price")
    @Min(value = 1, message = "Unit Price at least one (1)")
    private double unitPrice;

    @Column(name = "quantity")
    private int quantity;

}
