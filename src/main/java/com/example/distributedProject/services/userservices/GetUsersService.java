package com.example.distributedProject.services.userservices;

import com.example.distributedProject.Query;
import com.example.distributedProject.model.User;
import com.example.distributedProject.model.UserDTO;
import com.example.distributedProject.services.UserCheckService;
import com.example.distributedProject.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetUsersService implements Query<Void, List<UserDTO>> {

    private final UserRepository userRepository;
    private final UserCheckService userCheckService;


    public GetUsersService(UserRepository userRepository, UserCheckService userCheckService) {
        this.userRepository = userRepository;
        this.userCheckService = userCheckService;
    }

    @Override
    public ResponseEntity<List<UserDTO>> execute (Void input){

        Boolean userCheckResponse = userCheckService.isAdmin();
        if (!userCheckResponse) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ArrayList<UserDTO>());
        }

        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = users.stream().map(UserDTO::new).toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTOS);
    }

}
