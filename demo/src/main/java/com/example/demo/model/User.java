package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

}
