package com.example.distributedProject.services.userservices;

import com.example.distributedProject.Command;
import com.example.distributedProject.model.UpdateUserCommand;
import com.example.distributedProject.model.User;
import com.example.distributedProject.model.UserDTO;
import com.example.distributedProject.services.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateUserService implements Command<UpdateUserCommand, UserDTO> {
    private final UserRepository userRepository;

    public UpdateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<UserDTO> execute(UpdateUserCommand command){
        Optional<User> userOptional = userRepository.findById(command.getUuid());

        if(userOptional.isPresent()){
            User user = command.getUser();
            user.setUuid(command.getUuid());
            userRepository.save(user);
            return ResponseEntity.ok(new UserDTO(user));
        }
        return null;

    }
}
