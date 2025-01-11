package com.example.distributedProject.services;

import com.example.distributedProject.model.User;
import com.example.distributedProject.security.JwtGenerate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserCheckService {
    private final JwtGenerate jwtGenerate;
    private final HttpServletRequest request;
    private final UserRepository userRepository;

    public UserCheckService(JwtGenerate jwtGenerate, HttpServletRequest request, UserRepository userRepository) {
        this.jwtGenerate = jwtGenerate;
        this.request = request;
        this.userRepository = userRepository;
    }


    public Integer validateAndExtractUserId() {
        String token = extractToken(request);
        if (token != null && jwtGenerate.validateToken(token)) {
            return jwtGenerate.getUserIdFromToken(token);
        }
        return null; // Token geçersizse null döner
    }

    public Boolean isAdmin(){
        Integer userIdFromToken = validateAndExtractUserId();
        User user = userRepository.findById(userIdFromToken).orElseThrow();
        if(Objects.equals(user.getRole(), "ADMIN")){
            return true;
        }
        return false;
    }

    public ResponseEntity<Void> sameUserCheck(Integer userId) {
        Integer userIdFromToken = validateAndExtractUserId();

        if (userIdFromToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if(isAdmin()){
            return ResponseEntity.ok().build();
        }

        if (!userIdFromToken.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok().build(); // Aynı kullanıcı ise işlem yapılabilir
    }


    private String extractToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
