package com.example.internshipapplication.Student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

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
    @GetMapping("/{id}/skills")
    public ResponseEntity<Set<String>> getStudentSkills(@PathVariable Integer id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student.getSkillNames());
    }
}
