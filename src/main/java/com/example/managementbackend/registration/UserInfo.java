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
@NoArgsConstructor
@AllArgsConstructor

public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "firstName is required")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "lastName is required")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Email is not Valid")
    @NotNull(message = "Email is required")
    @Column(length = 255, unique = true, nullable = true)
    private String email;

    @NotNull(message = "password is required")
    @Column(name = "password")
    private String password;

    @Column
    private Timestamp createdDate;

    private Timestamp modifiedDate;

    private boolean isActive;

    private String authToken;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    private boolean isDeleted;

    private  Timestamp lastLoginTime;
}
