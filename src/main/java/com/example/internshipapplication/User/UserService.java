package com.example.internshipapplication.User;

import com.example.internshipapplication.Student.Student;
import com.example.internshipapplication.Student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Autowired
    public UserService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // ---- Snabbtest ADMIN (ingen DB, funkar med NoOpPasswordEncoder) ----
        if ("admin@local".equalsIgnoreCase(email)) {
            return User.builder()
                    .username("admin@local")
                    .password("admin")          // klartext eftersom du kör NoOpPasswordEncoder
                    .roles("ADMIN")
                    .build();
        }

        // ---- Student från databasen ----
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new UsernameNotFoundException("Student not found: " + email);
        }

        return User.builder()
                .username(student.getEmail())
                .password(student.getPassword()) // klartext i din dump → NoOp matchar
                .roles("STUDENT")
                .build();
    }
}
