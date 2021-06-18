
package com.spring.encuestas.controllers;

import com.spring.encuestas.Library.Paginator;
import com.spring.encuestas.model.Polls;
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
public class MainController {

    @Autowired
    public PollRepository _pollRepository; //inyectando interface

    @GetMapping(value = {"/main"})
    public String main(HttpSession misession, Model model, int page) {
        Object[] listPolls = new Object[3];
        Object[] user = (Object[]) misession.getAttribute("usersession");
        if (user != null) {
            long idUser = (long) user[5];
            //filtrando informacion de tabala encuestas
            List<Polls> polls = _pollRepository.findAll().stream()
                    .filter(p -> p.getUser_id() == idUser)
                    .collect(Collectors.toList());
            if (!polls.isEmpty()) {
                listPolls = new Paginator().paginator(polls, page, 1, "main", "http://localhost:8080/");
                model.addAttribute("listPolls", listPolls);
            } else {
                listPolls[0] = "No data";
                listPolls[1] = "No data";
                listPolls[2] = new ArrayList<Polls>();
                model.addAttribute("listPolls", listPolls);
            }
            return "main";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = {"/main/*"})
    public String errorMain() {
        return "error";
    }
}
