package com.hozhabr.demo.controller;


import com.hozhabr.demo.dto.UsersOutputDto;
import com.hozhabr.demo.exception.ApiRequestException;
import com.hozhabr.demo.model.Users;
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
@RequestMapping(value = "/v1/api/demo")
public class UsersController {

    private final static Logger logger = LoggerFactory.getLogger("UsersController.class");
    ResourceBundle resourceBundle = ResourceBundle.getBundle("errorMessage");

    private final UsersService usersService;
     @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/users")
    private ResponseEntity<String> saveUsers(@RequestBody Users users) {
        Optional<Users> user = usersService.getUsersByEmail(users.getEmail());
        if (!user.isEmpty()) {
            logger.error("Error happend");
            throw new ApiRequestException(" user is already registered");

        }
        usersService.save(users);
        return new ResponseEntity<>(resourceBundle.getString("message.successful"), HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UsersOutputDto> getUsersById(@PathVariable("userId") Long id) {
        Optional<Users> users = usersService.getUsersById(id);
        if (users.isEmpty()) {
            throw new ApiRequestException("Not found");
        }
        Users user = users.get();

        UsersOutputDto usersOutputDto = new UsersOutputDto();
        usersOutputDto.setUserName(user.getUserName());
        usersOutputDto.setPassword(user.getPassword());
        usersOutputDto.setEmail(user.getEmail());
        usersOutputDto.setUserId(user.getUserId());
        return new ResponseEntity<>(usersOutputDto, HttpStatus.OK);
    }

    @GetMapping("/users")
    private ResponseEntity<List<UsersOutputDto>> getAllUsers() {
        List<Users> users = usersService.getAllUsers();
        List<UsersOutputDto> usersOutputDtos = new ArrayList<>();
        for (Users user : users) {
            UsersOutputDto usersOutputDto = new UsersOutputDto();
            usersOutputDto.setUserName(user.getUserName());
            usersOutputDto.setPassword(user.getPassword());
            usersOutputDto.setEmail(user.getEmail());
            usersOutputDto.setUserId(user.getUserId());
            usersOutputDtos.add(usersOutputDto);
        }
        return new ResponseEntity<>(usersOutputDtos, HttpStatus.OK);
    }

    @PutMapping("/users/{userId}")
    private ResponseEntity<Long> update(@PathVariable Long userId) {
        Optional<Users> user = usersService.getUsersById(userId);
        if (user.isEmpty()) {
            throw new ApiRequestException("Not found");
        }
        Users result = user.get();
        result.setUserName("Name: " + result.getUserName());
        usersService.updateUsers(result);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }


    @DeleteMapping("/users/{userId}")
    public ResponseEntity<HttpStatus> deletePageById(@PathVariable("userId") Long userId) {
        Optional<Users> user = usersService.getUsersById(userId);
        if (user.isEmpty()) {
            throw new ApiRequestException("Not found");
        }
        usersService.deleteById(userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
