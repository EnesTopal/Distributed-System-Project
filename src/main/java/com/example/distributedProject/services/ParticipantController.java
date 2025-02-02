package com.example.distributedProject.services;


import com.example.distributedProject.model.Participant;
import com.example.distributedProject.model.ParticipantDTO;
import com.example.distributedProject.services.participantservices.CreateParticipateService;
import com.example.distributedProject.services.participantservices.DeleteParticipateService;
import com.example.distributedProject.services.participantservices.GetMyParticipantsService;
import com.example.distributedProject.services.participantservices.GetParticipantsService;
import com.example.distributedProject.services.userservices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ParticipantController {
    private final CreateParticipateService createParticipateService;
    private final DeleteParticipateService deleteParticipateService;
    private final GetParticipantsService getParticipantsService;
    private final GetMyParticipantsService getMyParticipantsService;

    public ParticipantController(CreateParticipateService createParticipateService,
                                 DeleteParticipateService deleteParticipateService,
                                 GetParticipantsService getParticipantsService, GetMyParticipantsService getMyParticipantsService) {
        this.createParticipateService = createParticipateService;
        this.deleteParticipateService = deleteParticipateService;
        this.getParticipantsService = getParticipantsService;
        this.getMyParticipantsService = getMyParticipantsService;
    }

    @PostMapping("/participate")
    public ResponseEntity<ParticipantDTO> createParticipate(@RequestBody ParticipantDTO participantDTO){
        return createParticipateService.execute(participantDTO);
    }

    @GetMapping("/participates")
    public ResponseEntity<List<ParticipantDTO>> getParticipates(){
        return getParticipantsService.execute(null);
    }

    @GetMapping("/myparticipates")
    public ResponseEntity<List<ParticipantDTO>> getMyParticipates(){
        return getMyParticipantsService.execute(null);
    }

    @DeleteMapping("/participate/{id}")
    public ResponseEntity<String> deleteParticipate(@PathVariable Integer id){
        return deleteParticipateService.execute(id);
    }
}
