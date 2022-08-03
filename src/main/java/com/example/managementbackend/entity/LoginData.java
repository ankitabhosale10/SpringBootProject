package com.example.managementbackend.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginData {

    private String email;
    @NotBlank(message = "password can not be empty")
    @Size(min = 3, max = 10, message = "password must be in between 3 to 10 Characters")
    private String password;

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }



    @Override
    public String toString() {
        return "LoginData{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
