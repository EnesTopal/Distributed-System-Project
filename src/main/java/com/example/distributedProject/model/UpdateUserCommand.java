package com.example.distributedProject.model;

import lombok.Getter;

@Getter
public class UpdateUserCommand {
    private Integer uuid;
    private User user;

    public UpdateUserCommand(Integer uuid, User user) {
        this.uuid = uuid;
        this.user = user;
    }

}
