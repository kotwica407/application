package com.company.application.controller;

import com.company.application.model.Worker;
import com.company.application.service.EmailService;
import com.company.application.service.TaskService;
import com.company.application.service.WorkerService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
public class MainController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TaskService taskService;
    private WorkerService workerService;
    private EmailService emailService;

    @Autowired
    public MainController(TaskService taskService, WorkerService workerService, EmailService emailService){
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder(11);
        this.workerService = workerService;
        this.taskService = taskService;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String home(HttpServletRequest request){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "custom-login";
    }

    @GetMapping("/admin/new-worker")
    public ModelAndView formularz(ModelAndView modelAndView, Worker worker){
        modelAndView.addObject("worker", worker);
        modelAndView.setViewName("formularz");
        return modelAndView;
    }

    @PostMapping("/admin/new-worker")
    public ModelAndView processFormularz(ModelAndView modelAndView, @Valid Worker worker, String password, BindingResult bindingResult, HttpServletRequest request){
        modelAndView.addObject("worker", worker);
        Optional<Worker> workerExistsLogin = workerService.findByLogin(worker.getLogin());
        Optional<Worker> workerExistsEmail = workerService.findByEmail(worker.getEmail());

        if(workerExistsLogin.isPresent() || workerExistsEmail.isPresent()){
            modelAndView.addObject("errorMessage", "Oops!  There is already a user registered with the login or email address provided.");
            modelAndView.setViewName("formularz");
            bindingResult.reject("login");
        }

        if(bindingResult.hasErrors()){
            modelAndView.setViewName("formularz");
        }
        else {
            worker.setPassword(bCryptPasswordEncoder.encode(worker.getPassword()));
            worker.setHiredate(new java.sql.Date(new java.util.Date().getTime()));
            workerService.saveWorker(worker);
            modelAndView.setViewName("formularz");
        }
        return modelAndView;
    }

}
