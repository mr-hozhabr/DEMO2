
package com.hozhabr.demo.service;


import com.hozhabr.demo.model.Users;
import com.hozhabr.demo.repository.TaskRepository;
import com.hozhabr.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Service

public class UsersService {

    private UsersRepository userRepository;

    @Autowired
    public UsersService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<Users> getAllUsers() {

        return userRepository.findAll();
    }

    public Optional<Users> getUsersById(Long id) {

        return userRepository.findById(id);
    }
    public Optional<Users> getUsersByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    public Long save(Users users) {

        return userRepository.save(users).getUserId();
    }

    public void deleteById(Long id) {
            userRepository.deleteById(id);
    }

    public Users updateUsers(Users users) {

        return userRepository.save(users);

    }


}

