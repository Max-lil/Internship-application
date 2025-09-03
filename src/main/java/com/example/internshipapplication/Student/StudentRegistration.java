package com.example.internshipapplication.Student;

import com.example.internshipapplication.exception.InvalidInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        try {
            if (student.getFirstName() == null || student.getFirstName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Förnamn får inte vara tomt.");
            }

            if (!student.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                return ResponseEntity.badRequest().body("Ogiltig e-postadress.");
            }

            if (studentService.emailExists(student.getEmail())) {
                return ResponseEntity.badRequest().body("E-postadressen är redan registrerad.");
            }

            Student savedStudent = studentService.addStudent(
                    student.getFirstName(), student.getLastName(), student.getLocation(),
                    student.getEmail(), student.getPhoneNumber(), null, null,
                    student.getEducation(), student.getPassword()
            );
            return ResponseEntity.ok(savedStudent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Misslyckades att registrera student: " + e.getMessage());
        }
    }
}
