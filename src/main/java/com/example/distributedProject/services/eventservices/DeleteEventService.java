package com.example.distributedProject.services.eventservices;

import com.example.distributedProject.Command;
import com.example.distributedProject.model.Event;
import com.example.distributedProject.services.EventRepository;
import com.example.distributedProject.services.UserCheckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteEventService implements Command<Integer, String> {
    private EventRepository eventRepository;
    private UserCheckService userCheckService;

    public DeleteEventService(EventRepository eventRepository, UserCheckService userCheckService) {
        this.eventRepository = eventRepository;
        this.userCheckService = userCheckService;
    }

    @Override
    public ResponseEntity<String> execute (Integer id){

        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()){
            Integer organizerId = eventOptional.get().getUser().getUuid();
            ResponseEntity<Void> userCheckResponse = userCheckService.sameUserCheck(organizerId);
            if (!userCheckResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Yetkisiz işlem: Bu kullanıcı bu işlemi gerçekleştiremez.");
            }
            eventRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Event başarıyla silindi");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event bulunamadı");
    }
}
