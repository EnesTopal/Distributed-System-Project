package com.example.distributedProject.model;

import lombok.Data;
import lombok.Getter;

import java.util.List;


@Data
public class EventDTO {
    private Integer uuid;
    private String event_name;
    private String event_description;
    private Integer user_id;
    private User user;
//    private List<Participant> participants;

    public EventDTO() {}

    public EventDTO(Event event) {
        this.uuid = event.getUuid();
        this.event_name = event.getEvent_name();
        this.event_description = event.getEvent_description();
        this.user_id = event.getUser().getUuid();
        this.user = event.getUser();
//        this.participants = event.getParticipants();
    }

}
