package com.company.application.dao;

import com.company.application.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkersRepository extends JpaRepository<Worker, Integer> {

    Optional<Worker> findByLogin(String login);
    Optional<Worker> findByEmail(String email);
}
