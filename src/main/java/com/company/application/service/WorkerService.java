package com.company.application.service;

import com.company.application.dao.WorkersRepository;
import com.company.application.model.Worker;
import com.company.application.model.WorkerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkerService implements UserDetailsService {

    @Autowired
    private WorkersRepository workersRepository;


    public Optional<Worker> findByEmail(String email){
        return workersRepository.findByEmail(email);
    }

    public void saveWorker(Worker worker){
        workersRepository.save(worker);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{
        Optional<Worker> optionalWorker = workersRepository.findByLogin(login);

        optionalWorker.orElseThrow(()->new UsernameNotFoundException("Login not found"));
        return optionalWorker.map(WorkerDetails::new).get();
    }
}
