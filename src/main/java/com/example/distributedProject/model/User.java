package com.example.distributedProject.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid")
    private Integer uuid;

    @Column(name = "user_name", nullable = false)
    private String user_name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String user_password;

}

//    @OneToMany
//    @JoinColumn(name = "organizer_id")
//    private List<Integer> organizedEvents = new ArrayList<>();

//    @OneToMany
//    @JoinColumn(name = "participated_events")
//    private List<Integer> participated_events = new ArrayList<>();
