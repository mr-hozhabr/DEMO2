package com.hozhabr.demo.controller;

import com.hozhabr.demo.dto.TasksOutputDto;
import com.hozhabr.demo.exception.ApiRequestException;
import com.hozhabr.demo.model.Tasks;
import com.hozhabr.demo.model.Users;
import com.hozhabr.demo.service.TasksService;
import com.hozhabr.demo.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@RestController
@RequestMapping(value = "/v1/api/tasks/demo")
public class TasksController {

        private final  Logger logger = LoggerFactory.getLogger("TasksController.class");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("errorMessage");

        private  final TasksService tasksService;
        private final UsersService usersService;

        @Autowired
        public TasksController(TasksService tasksService, UsersService usersService) {
        this.tasksService = tasksService;
        this.usersService = usersService;
    }

    @PostMapping("/tasks/{userId}")
        private  ResponseEntity<String> saveTasks(@PathVariable("userId") Long userId,@RequestBody Tasks tasks) {
            Optional<Users> optionalUsers = usersService.getUsersById(userId);
            if(optionalUsers.get() == null) {
                logger.error("Error happend");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }

            Users user = optionalUsers.get();
            if(tasks.getTaskId() != null) {
                Optional<Tasks> optionalTasks= tasksService.getTasksById(tasks.getTaskId());
                if(optionalTasks.get() != null) {
                    tasks.setUsers(user);
                    tasksService.save(tasks);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
              tasksService.save(new Tasks(tasks.getTitle(), tasks.getDescription(), user));
            }

            return new ResponseEntity<>(resourceBundle.getString("message.successful"), HttpStatus.CREATED);
        }

        @GetMapping("/tasks/{taskId}")
        public ResponseEntity<TasksOutputDto> getTaskById(@PathVariable("taskId") Long id) {
            Optional<Tasks> tasks = tasksService.getTasksById(id);
            if (tasks.isEmpty()) {
                throw new ApiRequestException("Not found");
            }
            Tasks task=tasks.get();
            TasksOutputDto tasksOutputDto=new TasksOutputDto();
            tasksOutputDto.setTaskId(task.getTaskId());
            tasksOutputDto.setTitle(task.getTitle());
            tasksOutputDto.setDescription(task.getDescription());
            return new ResponseEntity<>(tasksOutputDto, HttpStatus.OK);
        }

        @GetMapping("/tasks")
        private ResponseEntity<List<TasksOutputDto>> getAllTaskss() {
            List<Tasks> tasks = tasksService.getAllTasks();
            ArrayList<TasksOutputDto> tasksOutputDtos=new ArrayList<>();
            for (Tasks task: tasks ) {
                TasksOutputDto tasksOutputDto=new TasksOutputDto();
                tasksOutputDto.setTaskId(task.getTaskId());
                tasksOutputDto.setTitle(task.getTitle());
                tasksOutputDto.setDescription(task.getDescription());
                tasksOutputDtos.add(tasksOutputDto);
            }

            return new ResponseEntity<>(tasksOutputDtos, HttpStatus.OK);
        }


        @PutMapping("/tasks/{taskId}")
        private ResponseEntity<Long> update(@PathVariable("taskId") Long taskId) {
            Optional<Tasks> task = tasksService.getTasksById(taskId);
            if (task.isEmpty()) {
                throw new ApiRequestException("Not found");
            }
            Tasks result = task.get();
            result.setTitle("Name: " + result.getTitle());
            tasksService.updateTasks(result);
            return new ResponseEntity<>(taskId,HttpStatus.OK);
        }

        @DeleteMapping("/tasks/{taskId}")
        public ResponseEntity<HttpStatus> deletePageById(@PathVariable("taskId") Long taskId ) {
            Optional<Tasks> task = tasksService.getTasksById(taskId);
            if (task.isEmpty()) {
                throw new ApiRequestException("Not found");
            }
            tasksService.deleteById(taskId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

    }

