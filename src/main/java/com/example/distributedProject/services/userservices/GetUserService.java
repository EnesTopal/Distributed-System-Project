package com.example.distributedProject.services.userservices;

import com.example.distributedProject.Command;
import com.example.distributedProject.model.User;
import com.example.distributedProject.model.UserDTO;
import com.example.distributedProject.services.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.distributedProject.services.UserCheckService;

import java.util.Optional;

@Service
public class GetUserService implements Command<Integer, UserDTO> {
    private UserRepository userRepository;
    private final UserCheckService userCheckService;

    public GetUserService(UserCheckService userCheckService, UserRepository userRepository) {
        this.userCheckService = userCheckService;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<UserDTO> execute(Integer input) {
        // Kullanıcı doğrulamasını kontrol et
        ResponseEntity<Void> userCheckResponse = userCheckService.sameUserCheck(input);

        // Eğer doğrulama başarısızsa uygun bir yanıt döndür
        if (userCheckResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(userCheckResponse.getStatusCode()).build();
        }

        // Kullanıcıyı veritabanından bul
        Optional<User> userOptional = userRepository.findById(input);
        if (userOptional.isPresent()) {
            // Kullanıcı bulunduysa UserDTO ile yanıt döndür
            return ResponseEntity.ok(new UserDTO(userOptional.get()));
        }

        // Kullanıcı bulunamazsa 404 NOT FOUND döndür
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public User getOneUserByUserName(String Username) {
        return userRepository.findByUsername(Username);
    }
}
