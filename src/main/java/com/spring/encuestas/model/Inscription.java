/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.encuestas.model;

import java.util.Date;

/**
 *
 * @author adrian
 */
public class Inscription {

    private Long id;
    private String poll;
    private Integer responses;
    private String response;
    private Date date;

    public Inscription(Long id, String poll, Integer responses, String response, Date date) {
        this.id = id;
        this.poll = poll;
        this.responses = responses;
        this.response = response;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getPoll() {
        return poll;
    }

    public Integer getResponses() {
        return responses;
    }

    public String getResponse() {
        return response;
    }

    public Date getDate() {
        return date;
    }

   
}
