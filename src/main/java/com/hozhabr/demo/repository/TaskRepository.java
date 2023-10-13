package com.hozhabr.demo.repository;

import com.hozhabr.demo.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Tasks,Long> {
    Tasks findByTitle( String title);

}
