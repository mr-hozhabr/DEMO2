package com.hozhabr.demo.model;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "USERS")
@ToString
public class Users  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

   @OneToMany(cascade = CascadeType.ALL,mappedBy="users",orphanRemoval = true)
    private List<Tasks> tasks;

}
