package com.example.internshipapplication.User;

import com.example.internshipapplication.Student.Student;
import com.example.internshipapplication.Student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // ÄNDRAT: findByEmail istället för existsByEmail
        Student student = studentRepository.findByEmail(email);

        if (student == null) {
            throw new UsernameNotFoundException("Student not found: " + email);
        }

        // Skapa Spring Security User (returnera UserDetails)
        return org.springframework.security.core.userdetails.User.builder()
                .username(student.getEmail())
                .password(student.getPassword())
                .roles("STUDENT")
                .build();
    }
}
