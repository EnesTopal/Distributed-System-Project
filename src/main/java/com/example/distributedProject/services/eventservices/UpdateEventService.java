package com.example.distributedProject.services.eventservices;


import com.example.distributedProject.Command;
import com.example.distributedProject.model.*;
import com.example.distributedProject.services.EventRepository;
import com.example.distributedProject.services.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateEventService implements Command<UpdateEventCommand, EventDTO> {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public UpdateEventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<EventDTO> execute(UpdateEventCommand command){
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
