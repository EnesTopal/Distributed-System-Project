package com.example.distributedProject.services.userservices;

import com.example.distributedProject.Command;
import com.example.distributedProject.model.User;
import com.example.distributedProject.model.UserDTO;
import com.example.distributedProject.services.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetUserService implements Command<Integer, UserDTO> {
    private UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<UserDTO> execute(Integer input) {
        Optional<User> userOptional = userRepository.findById(input);
        if (userOptional.isPresent()){
            return ResponseEntity.ok(new UserDTO(userOptional.get()));
        }
        return null;

    }

    public User getOneUserByUserName(String Username) {
        return userRepository.findByUser_name(Username);
    }
}
