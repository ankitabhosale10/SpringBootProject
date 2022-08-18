package com.example.managementbackend.registration;

import lombok.*;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginData {

    @Email(message = "Email is not Valid")
    @NotNull(message = "Email is required")
    @Column(length = 255, unique = true, nullable = true)
    private String email;

    @NotNull(message = "password is required")
    @Column(name = "password")
    private String password;
}