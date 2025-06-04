package com.example.internshipapplication.Student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/id/{id}")
    public Student getStudentById(@PathVariable Integer id) {
        return studentService.getStudentById(id);
    }

    /**
     Skapa en student. CV är valfritt vid registrering.
     */
    @PostMapping("/add")
    public ResponseEntity<Student> addStudentWithOptionalCv(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String location,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam(required = false) MultipartFile cv
    ) {
        try {
            Student student = studentService.addStudent(firstName, lastName, location, email, phoneNumber, cv);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Lägg till eller byt CV i efterhand för en redan registrerad student.
     */
    @PutMapping("/{id}/cv")
    public ResponseEntity<String> uploadCvForExistingStudent(
            @PathVariable Long id,
            @RequestParam("cv") MultipartFile file
    ) {
        try {
            studentService.uploadCvForStudent(id, file);
            return ResponseEntity.ok("CV uppladdat!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fel vid uppladdning av CV.");
        }
    }
}
