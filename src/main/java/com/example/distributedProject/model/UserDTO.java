package com.example.distributedProject.model;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
public class UserDTO {
    private Integer uuid;
    private String username;
    private String email;
    private String user_password;
    private List<Event> events;
    private String role;


    public UserDTO(User user) {
        this.uuid = user.getUuid();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.user_password = user.getUser_password();
        this.events = user.getEvents();
        this.role = user.getRole();
    }
}
