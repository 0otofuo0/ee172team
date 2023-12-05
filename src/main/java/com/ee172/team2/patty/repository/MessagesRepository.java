package com.ee172.team2.patty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ee172.team2.patty.model.Messages;

public interface MessagesRepository extends JpaRepository<Messages, Integer> {

}
