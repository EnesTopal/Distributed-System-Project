package com.example.distributedProject.services.eventservices;

import com.example.distributedProject.Query;
import com.example.distributedProject.model.Event;
import com.example.distributedProject.model.EventDTO;
import com.example.distributedProject.services.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GetEventsService implements Query<Void, List<EventDTO>> {
    private final EventRepository eventRepository;

    public GetEventsService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<List<EventDTO>> execute (Void input){
        List<Event> events = eventRepository.findAll();
        List<EventDTO> eventDTOS = events.stream().map(EventDTO::new).toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(eventDTOS);
    }
}
