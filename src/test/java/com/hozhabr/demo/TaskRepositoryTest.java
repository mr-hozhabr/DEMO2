package com.hozhabr.demo;


import com.hozhabr.demo.model.Tasks;
import com.hozhabr.demo.repository.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void saveTaskTest() {
        Tasks task = new Tasks();
        task.setTitle("task 1");
        task.setDescription("java example");
        taskRepository.save(task);
        Assertions.assertThat(task.getTaskId()).isGreaterThan(0);
    }

    @Test
    public void getTask() {
        Tasks task = taskRepository.findById(1L).get();
        Assertions.assertThat(task.getTaskId()).isEqualTo(1);
    }

    @Test
    public void getListOfTask() {
        List<Tasks> tasks = taskRepository.findAll();
        Assertions.assertThat(tasks.size()).isGreaterThan(0);
    }

    @Test
    public void updateTask() {
        Tasks tasks = taskRepository.findById(1L).get();
        tasks.setTitle("task 2");
        Tasks tasksUpdated = taskRepository.save(tasks);
        Assertions.assertThat(tasksUpdated.getTitle()).isEqualTo("task 2");
    }

    @Test
    public void deleteTask() {
        Tasks book = taskRepository.findById(1L).get();
        taskRepository.delete(book);
        Tasks tasks = taskRepository.findByTitle("task 2");
        Assertions.assertThat(tasks).isNull();
    }
}

