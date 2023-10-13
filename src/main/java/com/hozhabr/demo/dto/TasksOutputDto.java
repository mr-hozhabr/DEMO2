package com.hozhabr.demo.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class TasksOutputDto {
    private Long taskId;
    private String title;
    private String description;
}
