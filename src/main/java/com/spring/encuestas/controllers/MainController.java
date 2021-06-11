/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.encuestas.controllers;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author adrian
 */
@Controller
public class MainController {
    
    @GetMapping(value = {"/main"})
    public String main(HttpSession misession){
        Object[] user = (Object[]) misession.getAttribute("usersession");
        if(user != null){
            return "main";
        }else{
            return "redirect:/login";
        }
    }
    @GetMapping(value = {"/main/*"})
    public String errorMain() {
        return "error";
    }
}
