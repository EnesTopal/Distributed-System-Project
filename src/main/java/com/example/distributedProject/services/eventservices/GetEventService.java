package com.example.distributedProject.services.eventservices;

import com.example.distributedProject.Command;
import com.example.distributedProject.model.Event;
import com.example.distributedProject.model.EventDTO;
import com.example.distributedProject.services.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class GetEventService implements Command<Integer, EventDTO> {
    private EventRepository eventRepository;

    public GetEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<EventDTO> execute(Integer input){
        Optional<Event> eventOptional = eventRepository.findById(input);
        if (eventOptional.isPresent()){
            return ResponseEntity.ok(new EventDTO(eventOptional.get()));
        }
        return null;
    }
}
