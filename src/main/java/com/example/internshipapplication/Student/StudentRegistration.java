package com.example.internshipapplication.Student;

import com.example.internshipapplication.exception.InvalidInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/register/student")
public class StudentRegistration {

    private final StudentService studentService;

    public StudentRegistration(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Skapa en student. CV är valfritt vid registrering.
     */
    @PostMapping
    public ResponseEntity<Student> addStudentWithOptionalCv(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String location,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam(required = false) MultipartFile cv
    ) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new InvalidInputException("Förnamn får inte vara tomt.");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidInputException("Ogiltig e-postadress.");
        }
        if (studentService.emailExists(email)) {
            throw new InvalidInputException("E-postadressen är redan registrerad.");
        }


        try {
            Student student = studentService.addStudent(firstName, lastName, location, email, phoneNumber, cv);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            throw new RuntimeException("Misslyckades att registrera student.", e);
        }
    }
}
