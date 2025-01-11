package com.example.distributedProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String user_password;

    // İlişkili Event'lerin otomatik silinmesi için CascadeType.REMOVE
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private List<Event> events;
}


//@Entity
//@Table(name = "user")
//@Data
//public class  User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "uuid")
//    private Integer uuid;
//
//    @Column(name = "user_name", nullable = false)
//    private String username;
//
//    @Column(name = "email", nullable = false)
//    private String email;
//
//    @Column(name = "user_password", nullable = false)
//    private String user_password;
//
//}


