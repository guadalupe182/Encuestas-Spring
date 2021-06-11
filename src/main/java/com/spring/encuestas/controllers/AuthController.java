/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.encuestas.controllers;

import com.spring.encuestas.Library.Encrypt;
import com.spring.encuestas.model.Login;
import com.spring.encuestas.model.User;
import com.spring.encuestas.repository.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    //injecting object
    @Autowired
    public UserRepository userRepository;

    @GetMapping(value = {"/signup"})
    public String signup(Model model, HttpSession session) {
        Object[] user = (Object[]) session.getAttribute("usersession");
        if (user != null) { 
            return "main";
        } else {
            model.addAttribute("user", new User());
            return "signup";
        }
    }

    @PostMapping(value = {"/signup"}) //obtaining registration data
    @Transactional
    public String signup(@Valid User user, BindingResult result, Model model, HttpSession session) throws Exception {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "signup";
        } else {
            List<User> userData = userRepository.findAll().stream()//filtrando infromacion de la collecion de usuarios
                    .filter(u -> u.getEmail().equals(user.getEmail()))//comparando datos de la bd con datos ingresados por usuario
                    .collect(Collectors.toList());//convierte la coleccion de datos en una coleccion de lista de objetos

            if (userData.isEmpty()) {//si es vacio devuelve true
                Date actualDate = new Date();
                user.setIsActive(Boolean.TRUE);
                user.setIsSuperUser(Boolean.FALSE);
                user.setPassword(Encrypt.encrypt(user.getPassword()));
                user.setDateJoined(actualDate); //registration date
                user.setLastLogin(actualDate); //start date of session
                userRepository.save(user); //inserting data to the database (dealing with exceptions)
                Object[] data = {
                    user.getEmail(),
                    user.getFirstName(),
                    user.getIsSuperUser(),
                    user.getLastName(),
                    user.getUserName(),
                    user.getId()
                };
                session.setAttribute("usersession", data);
                return "redirect:/main";
            } else {
                result.rejectValue("email", "error.user", "An account already exists for this email");
                model.addAttribute("user", user);
                return "signup";
            }
        }
    }

    @GetMapping(value = {"/login"})
    public String login(Model model, HttpSession session) {
        Object[] user = (Object[]) session.getAttribute("usersession");
        if (user != null) {
            return "main";
        } else {
            model.addAttribute("login", new Login());
            return "login";
        }
    }

    @PostMapping(value = {"/login"}) //obtaining registration data
    @Transactional
    public String login(@Valid Login login, BindingResult result, Model model, HttpSession session) throws Exception {
        if (result.hasErrors()) {
            model.addAttribute("login", login);
            return "login";
        } else {
            List<User> userData = userRepository.findAll().stream()//filtrando infromacion de la collecion de usuarios
                    .filter(u -> u.getEmail().equals(login.getEmail()))//comparando datos de la bd con datos ingresados por usuario
                    .collect(Collectors.toList());//convierte la coleccion de datos en una coleccion de lista de objetos

            if (userData.isEmpty()) {
                result.rejectValue("email", "error.user", "Unregistered email");
                model.addAttribute("login", login);
                return "login";
            } else {
                String pass = Encrypt.decrypt(userData.get(0).getPassword());//desencriptando contrasena
                if (pass.equals(login.getPassword())) {//validando si la contrasena es igual a la desencriptada
                    Object[] data = {
                        userData.get(0).getEmail(),
                        userData.get(0).getFirstName(),
                        userData.get(0).getIsSuperUser(),
                        userData.get(0).getLastName(),
                        userData.get(0).getUserName(),
                        userData.get(0).getId()
                    };
                    //almacenando datos en usuario de sesion
                    session.setAttribute("usersession", data);
                    return "redirect:/main";
                } else {
                    result.rejectValue("password", "error.user", "Wrong password");
                    model.addAttribute("login", login);
                    return "login";
                }
            }
        }
    }

    @GetMapping(value = {"/logout"})
    public String logout(HttpSession session) {
        session.setAttribute("usersession", null);
        return "redirect:/";
    }

    @GetMapping(value = {"/signup/*"})
    public String errorSignup(Model model) {
        model.addAttribute("error", "Error when executing the url in the signup");
        return "error";
    }

    @GetMapping(value = {"/login/*"})
    public String errorLogin() {
        return "error";
    }
}
