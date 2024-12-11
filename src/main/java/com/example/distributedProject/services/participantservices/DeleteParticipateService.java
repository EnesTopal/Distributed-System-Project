package com.example.distributedProject.services.participantservices;

import com.example.distributedProject.Command;
import com.example.distributedProject.model.Participant;
import com.example.distributedProject.services.ParticipantRepository;
import com.mysql.cj.x.protobuf.MysqlxCursor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteParticipateService implements Command<Integer,Void> {
    private ParticipantRepository participantRepository;

    public DeleteParticipateService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Override
    public ResponseEntity<Void> execute (Integer id){
        Optional<Participant> participantOptional = participantRepository.findById(id);
        if (participantOptional.isPresent()){
            participantRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return null;
    }
}
