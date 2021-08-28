/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.encuestas.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author adrian
 */
@Entity
@Table(name = "inscriptions")
@EntityListeners(AuditingEntityListener.class)
public class Inscriptions implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_in;
    private Long poll_id;
    private Long response_id;
    private String response;
    private Long user_id;
    @CreatedDate
    private Date date;

    public Inscriptions() {
    }

    public Inscriptions(Long id_in, Long poll_id, Long response_id, String response, Long user_id, Date date) {
        this.id_in = id_in;
        this.poll_id = poll_id;
        this.response_id = response_id;
        this.response = response;
        this.user_id = user_id;
        this.date = date;
    }

    public Long getId_in() {
        return id_in;
    }

    public void setId_in(Long id_in) {
        this.id_in = id_in;
    }

    public Long getPoll_id() {
        return poll_id;
    }

    public void setPoll_id(Long poll_id) {
        this.poll_id = poll_id;
    }

    public Long getResponse_id() {
        return response_id;
    }

    public void setResponse_id(Long response_id) {
        this.response_id = response_id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
