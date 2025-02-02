package com.example.distributedProject.services;

import com.example.distributedProject.model.UpdateUserCommand;
import com.example.distributedProject.model.User;
import com.example.distributedProject.model.UserDTO;
import com.example.distributedProject.services.userservices.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class UserController {
    private final CreateUserService createUserService;
    private final DeleteUserService deleteUserService;
    private final GetUserService getUserService;
    private final GetUsersService getUsersService;
    private final UpdateUserService updateUserService;

    public UserController(CreateUserService createUserService,
                          DeleteUserService deleteUserService,
                          GetUserService getUserService,
                          GetUsersService getUsersService,
                          UpdateUserService updateUserService) {
        this.createUserService = createUserService;
        this.deleteUserService = deleteUserService;
        this.getUserService = getUserService;
        this.getUsersService = getUsersService;
        this.updateUserService = updateUserService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers(){
        return getUsersService.execute(null);
    }

    @GetMapping("/user/{uuid}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer uuid){
        return getUserService.execute(uuid);
    }

    @PutMapping("/user/{uuid}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer uuid, @RequestBody User user){
        return updateUserService.execute(new UpdateUserCommand(uuid,user ));
    }

    @DeleteMapping("/user/{uuid}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer uuid){
        return deleteUserService.execute(uuid);
    }
}
