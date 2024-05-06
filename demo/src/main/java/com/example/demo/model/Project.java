package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "PROJECTS")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECTID")
    private int projectId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID", referencedColumnName = "ID")
    private User user;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    // Constructors, getters, and setters omitted for brevity
}
