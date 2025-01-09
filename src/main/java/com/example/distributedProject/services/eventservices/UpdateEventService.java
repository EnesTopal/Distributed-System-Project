package com.example.distributedProject.services.eventservices;


import com.example.distributedProject.Command;
import com.example.distributedProject.model.*;
import com.example.distributedProject.services.EventRepository;
import com.example.distributedProject.services.UserCheckService;
import com.example.distributedProject.services.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateEventService implements Command<UpdateEventCommand, EventDTO> {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final UserCheckService userCheckService;

    public UpdateEventService(EventRepository eventRepository, UserRepository userRepository, UserCheckService userCheckService) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.userCheckService = userCheckService;
    }

    @Override
    public ResponseEntity<EventDTO> execute(UpdateEventCommand command){


        Integer organizerId = eventRepository.findById(command.getUuid()).get().getUser().getUuid();
        ResponseEntity<Void> userCheckResponse = userCheckService.sameUserCheck(organizerId);
//        ResponseEntity<Void> userCheckResponse = userCheckService.sameUserCheck(command.getEventDTO().getUser_id());
        if (!userCheckResponse.getStatusCode().is2xxSuccessful()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new EventDTO());
        }

        System.out.println(command.getEventDTO().getUser_id());
        Optional<Event> eventOptional = eventRepository.findById(command.getEventDTO().getUuid());
        Optional<User>  userOptional = userRepository.findById(command.getEventDTO().getUser_id());
        if (eventOptional.isPresent() && userOptional.isPresent() ){
            Event event = new Event();
            event.setUuid(command.getEventDTO().getUuid());
            event.setEvent_name(command.getEventDTO().getEvent_name());
            event.setEvent_description(command.getEventDTO().getEvent_description());
            event.setUser(userOptional.get());
//            eventRepository.deleteById(command.getUuid());
            eventRepository.save(event);
            return ResponseEntity.ok(new EventDTO(event));
        }
        return null;
    }

}
