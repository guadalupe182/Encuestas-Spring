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
import java.util.Date;
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
            return "redirect:/main";
        } else {
            return "redirect:/login";
        }
    }

}
