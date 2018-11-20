package com.company.application.controller;

import com.company.application.model.Task;
import com.company.application.model.Worker;
import com.company.application.service.EmailService;
import com.company.application.service.TaskService;
import com.company.application.service.WorkerService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
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
    public ModelAndView home(ModelAndView modelAndView, Principal principal){
        final String loggedInUserName = principal.getName();
        Optional<Worker> worker = workerService.findByLogin(loggedInUserName);
        modelAndView.addObject("name", worker.get().getName()+" "+worker.get().getLastname());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/login")
    public String login(){
        return "custom-login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
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

    @GetMapping("/my-tasks")
    public ModelAndView myTasks(ModelAndView modelAndView, Principal principal) {
        final String loggedInUserName = principal.getName();
        Optional<Worker> worker = workerService.findByLogin(loggedInUserName);
        modelAndView.addObject("name", worker.get().getName()+" "+worker.get().getLastname());
        modelAndView.addObject("tasks", taskService.findTaskByWorkerId(worker.get().getIdWorker()));
        modelAndView.setViewName("tasks");
        return modelAndView;
    }

    @GetMapping("/give-task")
    public ModelAndView giveTask(@RequestParam int idWorker, ModelAndView modelAndView, Principal principal){
        final String loggedInUserName = principal.getName();
        Optional<Worker> worker = workerService.findByLogin(loggedInUserName);
        modelAndView.addObject("idWorker", idWorker);
        modelAndView.addObject("task", new Task());
        modelAndView.addObject("name", worker.get().getName()+" "+worker.get().getLastname());
        modelAndView.setViewName("give-task");
        return modelAndView;
    }

    @PostMapping("/give-task")
    public String saveTask(@RequestParam int idWorker, @ModelAttribute Task task, BindingResult bindingResult){
        task.setIdWorker(idWorker);
        taskService.save(task);
        return "my-inferiors";
    }

    @GetMapping("/my-inferiors")
    public ModelAndView myInferiors(ModelAndView modelAndView, Principal principal){
        final String loggedInUserName = principal.getName();
        Optional<Worker> worker = workerService.findByLogin(loggedInUserName);
        modelAndView.addObject("name", worker.get().getName()+" "+worker.get().getLastname());
        modelAndView.addObject("inferiors", workerService.findAllByDepartment(worker.get().getDepartment()));
        modelAndView.setViewName("my-inferiors");
        return modelAndView;
    }

    @GetMapping("/tasks")
    public ModelAndView tasks(@RequestParam int idWorker, ModelAndView modelAndView, Principal principal){
        Optional<Worker> worker = workerService.findWorkerById(idWorker);
        modelAndView.addObject("name", workerService.findByLogin(principal.getName()).get().getName()+" "+workerService.findByLogin(principal.getName()).get().getLastname());
        modelAndView.addObject("tasks", taskService.findTaskByWorkerId(worker.get().getIdWorker()));
        modelAndView.setViewName("tasks");
        return modelAndView;
    }
}
