package com.example.distributedProject.services.eventservices;

import com.example.distributedProject.Command;
import com.example.distributedProject.model.Event;
import com.example.distributedProject.model.EventDTO;
import com.example.distributedProject.model.User;
import com.example.distributedProject.services.EventRepository;
import com.example.distributedProject.services.UserRepository;
import com.mysql.cj.log.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateEventService implements Command<EventDTO, EventDTO> {
    private EventRepository eventRepository;
    private UserRepository userRepository;

    public CreateEventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<EventDTO> execute(EventDTO eventDTO){
        System.out.println(eventDTO.getUser_id());
        // user_id'yi alıp, ilgili User'ı veritabanından buluyoruz
        User user = userRepository.findById(eventDTO.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Event nesnesini oluşturuyoruz
        Event event = new Event();
        event.setEvent_name(eventDTO.getEvent_name());
        event.setEvent_description(eventDTO.getEvent_description());
        event.setUser(user);  // Kullanıcıyı Event ile ilişkilendiriyoruz

        // Event kaydediyoruz
        Event savedEvent = eventRepository.save(event);

        // Kaydedilen Event'i DTO'ya çevirip geri gönderiyoruz
        return ResponseEntity.status(HttpStatus.CREATED).body(new EventDTO(savedEvent));
    }
}
