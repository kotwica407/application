package com.company.application.service;

import com.company.application.dao.WorkersRepository;
import com.company.application.model.Worker;
import com.company.application.model.WorkerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkerService implements UserDetailsService {

    @Autowired
    private WorkersRepository workersRepository;


    public List<Worker> findAllByDepartment(String department){
        List<Worker> workers = new ArrayList<>();
        for(Worker worker: workersRepository.findAll()){
            if(worker.getDepartment().equals(department))
                workers.add(worker);
        }
        return workers;
    }

    public Optional<Worker> findByLogin(String login){
        return workersRepository.findByLogin(login);
    }

    public Optional<Worker> findByEmail(String email){
        return workersRepository.findByLogin(email);
    }

    public HashMap<String, String> findAll(){
        HashMap<String,String> workers = new HashMap<>();
        for(Worker worker: workersRepository.findAll()){
            workers.put(worker.getLogin(),worker.getPassword());
        }
        return workers;
    }

    public void saveWorker(Worker worker){
        workersRepository.save(worker);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{
        Optional<Worker> optionalWorker = workersRepository.findByLogin(login);

        if(optionalWorker.isPresent()==false)
            System.out.print("nie ma uzytkownika o loginie " + login);
        else
            System.out.print("znaleziono uzytkownika o loginie " + login);
        optionalWorker.orElseThrow(()->new UsernameNotFoundException("Login not found"));
        return optionalWorker.map(WorkerDetails::new).get();
    }
}
