/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.encuestas.controllers;

import com.spring.encuestas.model.Inscription;
import com.spring.encuestas.model.Inscriptions;
import com.spring.encuestas.model.Polls;
import com.spring.encuestas.repository.InscriptionsRepository;
import com.spring.encuestas.repository.PollRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author adrian
 */
@Controller
public class InscriptionsController {
    @Autowired
    public PollRepository _pollRepository;
    @Autowired
    public InscriptionsRepository _inscriptionsRepository;
    
    @GetMapping(value = {"/inscriptions"})
    public String inscriptions(Model model, HttpSession session, int page){
        Object[] user = (Object[]) session.getAttribute("usersession");
        if(user != null){
            Object[] listPolls = new Object[3];
            //consulta a id de user
            List<Inscription> list = new ArrayList<>();
            long id_user = (long) user[5];
            //consulta a tabla inscriptions
            List<Inscriptions> inscription = _inscriptionsRepository.findAll()
                    .stream().filter(p -> p.getUser_id() == id_user)
                    .collect(Collectors.toList());
            inscription.forEach(item -> {
                //buscando en tabla encuesta
            Polls poll = _pollRepository.findById(item.getPoll_id()).get();
              list.add(new Inscription(
                        poll.getId(),
                        poll.getPoll(),
                        poll.getResponses(),
                        item.getResponse(),
                        item.getDate()
                ));
            });
        }else{
            return "redirect:/login";
        }
        return "";
    }
    
}
