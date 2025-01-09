package com.example.distributedProject.services.userservices;

import com.example.distributedProject.Command;
import com.example.distributedProject.model.User;
import com.example.distributedProject.services.EventRepository;
import com.example.distributedProject.services.ParticipantRepository;
import com.example.distributedProject.services.UserCheckService;
import com.example.distributedProject.services.UserRepository;
import com.example.distributedProject.security.JwtGenerate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class DeleteUserService implements Command<Integer, String> {
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;
    private final UserCheckService userCheckService;

    public DeleteUserService(UserRepository userRepository,
                             ParticipantRepository participantRepository,
                             EventRepository eventRepository,
                             UserCheckService userCheckService) {
        this.userRepository = userRepository;
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
        this.userCheckService = userCheckService;
    }

//    @Override
//    public ResponseEntity<Void> execute(Integer id) {
//        // Kullanıcı kontrolü
//        ResponseEntity<Void> userCheckResponse = userCheckService.sameUserCheck(id);
//        if (!userCheckResponse.getStatusCode().is2xxSuccessful()) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Eğer kontrol başarısızsa uygun yanıt döndür
//        }
//
//        // Kullanıcıyı kontrol et
//        Optional<User> userOptional = userRepository.findById(id);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//
//            // İlişkili participant ve event verilerini temizle
//            participantRepository.deleteById(user.getUuid());
//            eventRepository.deleteById(user.getUuid());
//
//            // Kullanıcıyı sil
//            userRepository.delete(user);
//
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }

    @Override
    public ResponseEntity<String> execute(Integer id) {
        // Kullanıcı kontrolü
        ResponseEntity<Void> userCheckResponse = userCheckService.sameUserCheck(id);
        if (!userCheckResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Yetkisiz işlem: Bu kullanıcı bu işlemi gerçekleştiremez.");
        }

        // Kullanıcıyı kontrol et
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // İlişkili participant ve event verilerini temizle
            participantRepository.deleteById(user.getUuid());
            eventRepository.deleteById(user.getUuid());

            // Kullanıcıyı sil
            userRepository.delete(user);

            // Başarılı silme durumu için mesaj döndür
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Başarılı: Kullanıcı ve ilişkili veriler başarıyla silindi.");
        }

        // Kullanıcı bulunamadı durumu için mesaj döndür
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Kullanıcı bulunamadı: Verilen ID ile eşleşen bir kullanıcı bulunamadı.");
    }


}
