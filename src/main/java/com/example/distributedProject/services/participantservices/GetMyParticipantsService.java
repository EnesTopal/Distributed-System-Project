package com.example.distributedProject.services.participantservices;

import com.example.distributedProject.Query;
import com.example.distributedProject.model.Participant;
import com.example.distributedProject.model.ParticipantDTO;
import com.example.distributedProject.security.JwtGenerate;
import com.example.distributedProject.services.ParticipantRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMyParticipantsService implements Query<Void, List<ParticipantDTO>> {
    private final ParticipantRepository participantRepository;
    private final JwtGenerate jwtGenerate;
    private final HttpServletRequest request;

    private String extractToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    public Integer validateAndExtractUserId() {
        String token = extractToken(request);
        if (token != null && jwtGenerate.validateToken(token)) {
            return jwtGenerate.getUserIdFromToken(token);
        }
        return null; // Token geçersizse null döner
    }

    public GetMyParticipantsService(ParticipantRepository participantRepository, JwtGenerate jwtGenerate, HttpServletRequest request) {
        this.participantRepository = participantRepository;
        this.jwtGenerate = jwtGenerate;
        this.request = request;
    }

    @Override
    public ResponseEntity<List<ParticipantDTO>> execute(Void input) {

        List<Participant> participants = participantRepository.findAll();
        List<ParticipantDTO> participantDTOS = participants.stream()
                .filter(participant -> participant.getUser().getUuid().equals(validateAndExtractUserId()))
                .map(participant -> new ParticipantDTO(
                        participant.getUser().getUuid(),
                        participant.getEvent().getUuid(),
                        participant.getUuid()
                ))
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(participantDTOS);
//        List<Participant> participants = participantRepository.findAll();
//        List<ParticipantDTO> participantDTOS = participants.stream()
//                .map(participant ->
//                        new ParticipantDTO(
//                        participant.getUser().getUuid(),
//                        participant.getEvent().getUuid(),
//                        participant.getUuid()
//                ))
//                .toList();
//

    }

}

