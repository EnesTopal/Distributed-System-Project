package com.example.distributedProject.services;

import com.example.distributedProject.model.UpdateUserCommand;
import com.example.distributedProject.model.User;
import com.example.distributedProject.model.UserDTO;
import com.example.distributedProject.services.userservices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user){
        return createUserService.execute(user);
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
    public ResponseEntity<Void> deleteUser(@PathVariable Integer uuid){
        return deleteUserService.execute(uuid);
    }
}
