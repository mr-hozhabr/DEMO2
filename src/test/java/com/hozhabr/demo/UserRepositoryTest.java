package com.hozhabr.demo;


import com.hozhabr.demo.model.Users;

import com.hozhabr.demo.repository.UsersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UsersRepository UsersRepository;

    @Test
    public void saveUsersTest() {
        Users users = new Users();
        users.setUserName("User 1");
        users.setPassword("password");
        users.setEmail("email");
        UsersRepository.save(users);
        Assertions.assertThat(users.getUserId()).isGreaterThan(0);
    }

    @Test
    public void getUsers() {
        Users users = UsersRepository.findById(1L).get();
        Assertions.assertThat(users.getUserId()).isEqualTo(1);
    }

    @Test
    public void getListOfUsers() {
        List<Users> users = UsersRepository.findAll();
        Assertions.assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    public void updateUsers() {
        Users users = UsersRepository.findById(1L).get();
        users.setUserName("User 2");
        Users usersUpdated = UsersRepository.save(users);
        Assertions.assertThat(usersUpdated.getUserName()).isEqualTo("User 2");
    }

    @Test
    public void deleteUsers() {
        Users users = UsersRepository.findById(1L).get();
        UsersRepository.delete(users);
        Optional<Users> user = UsersRepository.findByEmail("email");
        Assertions.assertThat(user.get()).isNull();
    }
}

