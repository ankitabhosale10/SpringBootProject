package com.example.managementbackend.web.DTO;

import com.example.managementbackend.entity.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfoDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }


    @JsonIgnore
    public UserInfo getEntity() {
        UserInfo entity = new UserInfo();
        entity.setId(this.id);
        entity.setFirstName(this.firstName);
        entity.setLastName(this.lastName);
        entity.setEmail(this.email);
        entity.setPassword(this.password);
        return entity;
    }

}
