package com.example.distributedProject.model;


import com.example.distributedProject.services.EventRepository;
import com.example.distributedProject.services.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;

@Getter
public class UpdateEventCommand {
    private Integer uuid;
    private EventDTO eventDTO;

    public UpdateEventCommand(Integer uuid, EventDTO eventDTO) {
        this.uuid = uuid;
        this.eventDTO = eventDTO;
    }
}
