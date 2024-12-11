package com.example.distributedProject.model;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
public class UserDTO {
    private Integer uuid;
    private String user_name;
    private String email;
    private String user_password;
//    private List<Event> organizedEvents;
//    private List<Participant> participated_events;

    public UserDTO(User user) {
        this.uuid = user.getUuid();
        this.user_name = user.getUser_name();
        this.email = user.getEmail();
        this.user_password = user.getUser_password();
//        this.organizedEvents = user.getOrganizedEvents();
//        this.participated_events = user.getParticipated_events();
    }
}
