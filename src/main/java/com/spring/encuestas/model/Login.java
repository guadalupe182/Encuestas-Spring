/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.encuestas.model;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author adrian
 */
public class Login {
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "email is required")
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
