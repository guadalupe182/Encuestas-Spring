/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.encuestas.controllers;

import com.spring.encuestas.model.Polls;
import com.spring.encuestas.model.Response;
import com.spring.encuestas.repository.PollRepository;
import com.spring.encuestas.repository.ResponseRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author adrian
 */
@Controller
public class PollsController {

    @Autowired
    public PollRepository _pollRepository;
    @Autowired
    public ResponseRepository _responseRepository;

    @GetMapping(value = {"/createpoll"})
    public String createPoll(Model model, HttpSession session) {
        Object[] user = (Object[]) session.getAttribute("usersession");
        if (user != null) {
            return "polls/createPoll";
        } else {
            return "signup";
        }
    }

    @PostMapping(value = {"/registerpoll"})
    public String registerPoll(String[] response, String poll, HttpSession session) {
        Object[] user = (Object[]) session.getAttribute("usersession");
        if (user != null) {
            Date actualDate = new Date();
            Polls polls = new Polls();
            polls.setPoll(poll);
            polls.setResponse(response.length);
            polls.setDate(actualDate);
            polls.setUser_id((Long) user[5]);
            long id = _pollRepository.save(polls).getId();//obteniendo id del registro en la tabla
            for (String item : response) { //insertando respuestas en la tabla 
                Response responses = new Response();
                responses.setResponse(item);
                responses.setVotes(0);
                responses.setPolls_id(id);
                _responseRepository.save(responses);
            }
            return "redirect:/main?page=0";
        } else {
            return "redirect:/login";
        }
    }

    /**
     *
     * @param misession realiza una peticion http
     * @param model devuleve el model del objecto
     * @param id devuelve registro por id de usuario
     * @return retorna a la vista details
     */
    @GetMapping(value = {"/details"})
    public String details(HttpSession misession, Model model, long id) {
        //Creando consultas
        Polls poll = _pollRepository.findById(id).get();
        //Filtrando respuestas por id de usuario
        List<Response> response = _responseRepository.findAll()
                .stream().filter(p -> p.getPolls_id() == id)
                .collect(Collectors.toList());
        int votes = 0;
        int count = response.size(); //devuelve cantidad de respuestas que hay en objeto response
        List<Object> response1 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            response1.add(response.get(i).getResponse());
            response1.add(response.get(i).getVotes());
            votes += response.get(i).getVotes();//obteniendo cantidad total de votos que contiene la encuesta
        }
        model.addAttribute("poll", poll);
        model.addAttribute("response", response1);
        model.addAttribute("votes", votes);
        return "polls/details";
    }

}
