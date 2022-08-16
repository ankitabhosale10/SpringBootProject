package com.example.managementbackend.registration;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "user")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "first_name")
    private String firstName;


    @Column(name = "last_name")
    private String lastName;


    @Column(length = 255, unique = true, nullable = true)
    private String email;


    @Column(name = "password")
    private String password;

    @Column
    private Timestamp createdDate;

    private Timestamp modifiedDate;

    private boolean isActive;

    private String authToken;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

}
