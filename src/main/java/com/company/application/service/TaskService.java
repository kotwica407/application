package com.company.application.service;

import com.company.application.dao.TaskRepository;
import com.company.application.model.Task;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Optional<Task> findTaskById(int id){
        Optional<Task> task = taskRepository.findById(id);
        return task;
    }

    public List<Task> findTaskByWorkerId(int id){
        List<Task> tasks = new ArrayList<Task>();
        for(Task task : taskRepository.findAll()){
            if(task.getIdWorker()==id)
                tasks.add(task);
        }
        return tasks;
    }

    public void save(Task task){
        taskRepository.save(task);
    }

    public void delete(int id){
        taskRepository.deleteById(id);
    }
}
