package com.example.distributedProject.services.participantservices;

import com.example.distributedProject.Command;
import com.example.distributedProject.model.Event;
import com.example.distributedProject.model.Participant;
import com.example.distributedProject.model.ParticipantDTO;
import com.example.distributedProject.model.User;
import com.example.distributedProject.services.EventRepository;
import com.example.distributedProject.services.ParticipantRepository;
import com.example.distributedProject.services.UserRepository;
import jakarta.servlet.http.Part;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateParticipateService implements Command<ParticipantDTO, ParticipantDTO> {
    private final ParticipantRepository participantRepository;
    public final EventRepository eventRepository;
    public final UserRepository userRepository;

    public CreateParticipateService(ParticipantRepository participantRepository,
                                    EventRepository eventRepository,
                                    UserRepository userRepository) {
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<ParticipantDTO> execute(ParticipantDTO participantDTO){

        User user = userRepository.findById(participantDTO.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Event event= eventRepository.findById(participantDTO.getEvent_id())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Participant participant = new Participant();
        participant.setEvent(event);
        participant.setUser(user);
        Participant savedParticipant = participantRepository.save(participant);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ParticipantDTO(savedParticipant.getUser().getUuid(),savedParticipant.getEvent().getUuid(), savedParticipant.getUuid()));
    }
}
