package Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // För @PreAuthorize
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Definiera vilka URLs som kräver inlogg
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**", "/css/**", "/js/**").permitAll() // Öppna endpoints
                        .anyRequest().authenticated() // Alla andra kräver inlogg
                )
                // Konfigurera login-formulär
                .formLogin(form -> form
                        .loginPage("/login") // Custom login-sida (valfritt)
                        .defaultSuccessUrl("/home", true) // Dit användaren skickas efter inlogg
                        .permitAll() // Login-sidan ska vara öppen för alla
                )
                // Konfigurera logout
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout") // Dit användaren skickas efter utlogg
                        .permitAll()
                );

        return http.build();
    }
}
