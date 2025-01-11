package com.example.distributedProject.services.participantservices;

import com.example.distributedProject.Query;
import com.example.distributedProject.model.Participant;
import com.example.distributedProject.model.ParticipantDTO;
import com.example.distributedProject.model.UserDTO;
import com.example.distributedProject.services.ParticipantRepository;
import com.example.distributedProject.services.UserCheckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetParticipantsService implements Query<Void, List<ParticipantDTO>> {
    private final ParticipantRepository participantRepository;
    private final UserCheckService userCheckService;

    public GetParticipantsService(ParticipantRepository participantRepository, UserCheckService userCheckService) {
        this.participantRepository = participantRepository;
        this.userCheckService = userCheckService;
    }

    @Override
    public ResponseEntity<List<ParticipantDTO>> execute (Void input){

        Boolean userCheckResponse = userCheckService.isAdmin();
        if (!userCheckResponse) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ArrayList<ParticipantDTO>());
        }

        List<Participant> participants = participantRepository.findAll();
        List<ParticipantDTO> participantDTOS = participants.stream()
                .map(participant -> new ParticipantDTO(
                        participant.getUser().getUuid(),
                        participant.getEvent().getUuid(),
                        participant.getUuid()
                ))
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(participantDTOS);
    }
}
