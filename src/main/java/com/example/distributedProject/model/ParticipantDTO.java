package com.example.distributedProject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParticipantDTO {
    private Integer uuid;
    private User user;
    private Integer user_id;
    private Integer event_id;
    private Event event;

    public ParticipantDTO(){}

    public ParticipantDTO(Participant participant) {
        this.uuid = participant.getUuid();
        this.user = participant.getUser();
        this.user_id = participant.getUuid();
        this.event_id = participant.getUuid();
        this.event = participant.getEvent();
    }

    public ParticipantDTO(Integer user_id, Integer event_id, Integer uuid){
        this.uuid = uuid;
        this.event_id = event_id;
        this.user_id = user_id;
    }
}
