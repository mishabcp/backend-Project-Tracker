package com.example.demo.dto;

import lombok.Data;

@Data
public class ProjectRequest {
    private int userId;
    private String title;
    private String description;
}
