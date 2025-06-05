package com.example.internshipapplication.Student;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/register/student")
public class StudentRegistration {

    private final StudentService studentService;

    public StudentRegistration(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     Skapa en student. CV är valfritt vid registrering.
     */
    @PostMapping
    public ResponseEntity<Student> addStudent(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String location,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam(required = false) MultipartFile cv,
            @RequestParam(required = false) List<String> skills
    ) {
        try {
            Student student = studentService.addStudent(firstName, lastName, location, email, phoneNumber, cv, skills);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    /**
     * Lägg till eller byt CV i efterhand för en redan registrerad student.
     */
}
