/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.encuestas.repository;

import com.spring.encuestas.model.Polls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author adrian
 */
@Repository
public interface PollRepository extends JpaRepository<Polls, Long>{
    
}
