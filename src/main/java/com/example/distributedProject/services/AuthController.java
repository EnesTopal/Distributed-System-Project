package com.example.distributedProject.services;

import com.example.distributedProject.model.User;
import com.example.distributedProject.requests.UserRequest;
import com.example.distributedProject.security.JwtGenerate;
import com.example.distributedProject.services.userservices.CreateUserService;
import com.example.distributedProject.services.userservices.GetUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private JwtGenerate jwtGenerate;
    private CreateUserService createUserService;
    private GetUserService getUserService;
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtGenerate jwtGenerate, CreateUserService createUserService, GetUserService getUserService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerate = jwtGenerate;
        this.createUserService = createUserService;
        this.getUserService = getUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserRequest loginRequest){
        System.out.println(loginRequest.getUsername());
        System.out.println(loginRequest.getUser_password());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getUser_password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtGenerate.generateJwtToken(authentication);
        return "Bearer" + jwtToken;
    }
    //responseentity dönmemizin sebebi header da bilgisini vermek istersek diye
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User registerRequest){
        if(getUserService.getOneUserByUserName(registerRequest.getUsername())!=null){
            return new ResponseEntity<>("Username already in use", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setUser_password(passwordEncoder.encode(registerRequest.getUser_password()));
        user.setEmail(registerRequest.getEmail());
        createUserService.execute(user);
        return new ResponseEntity<>("User succesfully registered",HttpStatus.CREATED);
    }
}
