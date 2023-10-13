
package com.hozhabr.demo.service;


import com.hozhabr.demo.model.Tasks;
import com.hozhabr.demo.model.Users;
import com.hozhabr.demo.repository.TaskRepository;
import com.hozhabr.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Service

public class TasksService {

    @Autowired
    private TaskRepository taskRepository;



    public TasksService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Tasks> getAllTasks() {

        return taskRepository.findAll();
    }

    public Optional<Tasks> getTasksById(Long id) {

        return taskRepository.findById(id);
    }

    public Long save(Tasks tasks) {

        return taskRepository.save(tasks).getTaskId();
    }

    public void deleteById(Long id) {

        taskRepository.deleteById(id);

    }

    public Tasks updateTasks(Tasks tasks) {

        return taskRepository.save(tasks);

    }


}

