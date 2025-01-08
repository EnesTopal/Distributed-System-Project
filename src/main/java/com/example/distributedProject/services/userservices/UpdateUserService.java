//package com.example.distributedProject.services.userservices;
//
//import com.example.distributedProject.Command;
//import com.example.distributedProject.model.UpdateUserCommand;
//import com.example.distributedProject.model.User;
//import com.example.distributedProject.model.UserDTO;
//import com.example.distributedProject.services.UserRepository;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UpdateUserService implements Command<UpdateUserCommand, UserDTO> {
//    private final UserRepository userRepository;
//
//    public UpdateUserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public ResponseEntity<UserDTO> execute(UpdateUserCommand command){
//        Optional<User> userOptional = userRepository.findById(command.getUuid());
//
//        if(userOptional.isPresent()){
//            User user = command.getUser();
//            user.setUuid(command.getUuid());
//            userRepository.save(user);
//            return ResponseEntity.ok(new UserDTO(user));
//        }
//        return null;
//
//    }
//}

package com.example.distributedProject.services.userservices;

import com.example.distributedProject.Command;
import com.example.distributedProject.model.UpdateUserCommand;
import com.example.distributedProject.model.User;
import com.example.distributedProject.model.UserDTO;
import com.example.distributedProject.services.UserCheckService;
import com.example.distributedProject.services.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateUserService implements Command<UpdateUserCommand, UserDTO> {
    private final UserRepository userRepository;
    private final UserCheckService userCheckService;
    private PasswordEncoder passwordEncoder;

    public UpdateUserService(UserRepository userRepository, UserCheckService userCheckService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userCheckService = userCheckService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<UserDTO> execute(UpdateUserCommand command) {
        // Kullanıcı kontrolü
        ResponseEntity<Void> userCheckResponse = userCheckService.sameUserCheck(command.getUuid());
        if (!userCheckResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(userCheckResponse.getStatusCode()).build();
        }

        // Kullanıcıyı veritabanında kontrol et
        Optional<User> userOptional = userRepository.findById(command.getUuid());
        if (userOptional.isPresent()) {
            User user = command.getUser();
            user.setUuid(command.getUuid());
            user.setUser_password(passwordEncoder.encode(command.getUser().getUser_password()));

            // Güncellenen kullanıcıyı kaydet
            userRepository.save(user);

            // Güncellenen kullanıcıyı DTO olarak döndür
            return ResponseEntity.ok(new UserDTO(user));
        }

        // Kullanıcı bulunamazsa 404 NOT FOUND döndür
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

