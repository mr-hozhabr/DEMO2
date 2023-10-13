package com.hozhabr.demo.model;

import javax.persistence.*;
import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "TASKS")
public class Tasks  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false)
    private String title;
    @Column
    private String description;

    public Tasks(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Tasks() {
    }

    public Tasks(String title, String description, Users users) {
        this.title = title;
        this.description = description;
        this.users = users;
    }

    @ManyToOne
    @JoinColumn(name="userId")
    private Users users;
}
