package com.example.distributedProject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;


@Entity
@Data
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid")
    private Integer uuid;

    @Column(name = "event_name", nullable = false)
    private String event_name;

    @Column(name = "event_description", nullable = false)
    private String event_description;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    @JsonBackReference
    private User user;

    // İlişkili Participant'ların otomatik silinmesi için CascadeType.REMOVE
    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private List<Participant> participants = new ArrayList<>();
}



//@Entity
//@Data
//@Table(name = "event")
//public class Event {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "uuid")
//    private Integer uuid;
//
//    @Column(name = "event_name", nullable = false)
//    private String  event_name;
//
//    @Column(name = "event_description", nullable = false)
//    private String  event_description;
//
//    @ManyToOne
//    @JoinColumn(name = "organizer_id", nullable = false)
//    private User user;
//
//}

