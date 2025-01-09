package com.example.distributedProject.services;

import com.example.distributedProject.model.*;
import com.example.distributedProject.services.eventservices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {
    private final CreateEventService createEventService;
    private final DeleteEventService deleteEventService;
    private final GetEventService getEventService;
    private final GetEventsService getEventsService;
    private final UpdateEventService updateEventService;

    public EventController(CreateEventService createEventService,
                           DeleteEventService deleteEventService,
                           GetEventService getEventService,
                           GetEventsService getEventsService,
                           UpdateEventService updateEventService) {
        this.createEventService = createEventService;
        this.deleteEventService = deleteEventService;
        this.getEventService = getEventService;
        this.getEventsService = getEventsService;
        this.updateEventService = updateEventService;
    }

    @PostMapping("/event")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO){
        return createEventService.execute(eventDTO);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> getEvents(){
        return getEventsService.execute(null);
    }

    @GetMapping("/event/{uuid}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Integer uuid){
        return getEventService.execute(uuid);
    }

    @PutMapping("/event/{uuid}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Integer uuid, @RequestBody EventDTO eventDTO){
        return updateEventService.execute(new UpdateEventCommand(uuid,eventDTO));
    }

    @DeleteMapping("/event/{uuid}")
    public ResponseEntity<String> deleteEvent(@PathVariable Integer uuid){
        return deleteEventService.execute(uuid);
    }


}
