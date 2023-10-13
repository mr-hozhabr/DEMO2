package com.hozhabr.demo.dto;

import lombok.Data;

@Data
public class UsersOutputDto {
    private Long userId;
    private String userName;
    private String email;
    private String password;
}
