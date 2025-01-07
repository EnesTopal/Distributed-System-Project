package com.example.distributedProject.config;

import com.example.distributedProject.security.JwtAuthEntryPoint;
import com.example.distributedProject.security.JwtAuthFilter;
import com.example.distributedProject.services.JwtUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    private JwtUserDetailsService jwtUserDetailsService;
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    public SecurityConfig(JwtUserDetailsService jwtUserDetailsService, JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(){
        return new JwtAuthFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        JwtAuthEntryPoint handler = new JwtAuthEntryPoint();
        httpSecurity
                .cors(cors -> cors.configure(httpSecurity)) // Modern CORS yapılandırması
                .csrf(csrf -> csrf.disable()) // CSRF korumasını devre dışı bırak
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(handler)) // Hata yönetimi
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless oturum yönetimi
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Authentication endpointlerine izin
                        .anyRequest().authenticated() // Diğer tüm istekler için kimlik doğrulama gerektir
                );

        // JWT filtre eklenmesi
        httpSecurity.addFilterBefore(
                new JwtAuthFilter(),
                UsernamePasswordAuthenticationFilter.class
        );

        return httpSecurity.build();
    }

}
