package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "TASKS")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASKID")
    private int taskId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROJECTID", referencedColumnName = "PROJECTID")
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID", referencedColumnName = "ID")
    private User user;

    @Column(name = "TITLE",  nullable = false)
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS", columnDefinition = "boolean default false")
    private boolean completed;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    // Constructors, getters, and setters omitted for brevity
}
