package com.example.distributedProject.security;
import com.example.distributedProject.services.JwtUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    JwtGenerate jwtGenerate;
    @Autowired
    JwtUserDetailsService jwtUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = extractToken(request);
            if (StringUtils.hasText(jwt) && jwtGenerate.validateToken(jwt)) {
                Integer id = jwtGenerate.getUserIdFromToken(jwt);
                UserDetails user = jwtUserDetailsService.loadUserByid(id);
                if (user != null){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            return;
        }
        filterChain.doFilter(request,response);

    }

    private String extractToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer "))
            return bearer.substring("Bearer".length() + 1);

        return null;

    }
}
