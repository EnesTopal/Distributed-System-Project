package com.example.distributedProject.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;



@Entity
@Data
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid")
    private Integer uuid;

    @Column(name = "event_name", nullable = false)
    private String  event_name;

    @Column(name = "event_description", nullable = false)
    private String  event_description;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private User user;

}

//    @OneToMany
//    @JoinColumn(name = "event_id")
//    private List<Integer> participants = new ArrayList<>();