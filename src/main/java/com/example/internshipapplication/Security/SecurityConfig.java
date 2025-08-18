package com.example.internshipapplication.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // för @PreAuthorize som i guiden
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**", "/css/**", "/js/**").permitAll()
                        // Exempel på rollbaserade regler (kan justeras):
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("STUDENT","ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // .loginPage("/login") // Avkommentera om du har en egen vy
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                // Konfigurera logout
                .logout(logout -> logout
                        
                        .permitAll()

                );

        return http.build();
    }

    // Testläge: inga hashade lösenord – matchar dumpens klartext
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
