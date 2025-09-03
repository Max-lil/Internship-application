package com.example.internshipapplication.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    // NY ENDPOINT - Hämta inloggad students profil
    @GetMapping("/me")
    public ResponseEntity<Student> getMyProfile(Authentication auth) {
        try {
            String email = auth.getName();
            Optional<Student> student = studentRepository.findByEmail(email);
            if (student.isPresent()) {
                return ResponseEntity.ok(student.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Lägg till CV för inloggad student
    @PutMapping("/me/cv")
    public ResponseEntity<String> uploadMyCV(
            @RequestParam("cv") MultipartFile file,
            Authentication auth) {
        try {
            String email = auth.getName();
            Optional<Student> student = studentRepository.findByEmail(email);
            if (student.isPresent()) {
                studentService.updateStudentCv(student.get().getId(), file);
                return ResponseEntity.ok("CV uppladdat!");
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fel vid CV-uppladdning");
        }
    }


    //Hämta/ladda ned cv:t
    @GetMapping("/cv/{filename}")
    public ResponseEntity<Resource> downloadCV(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("uploads/cv/").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Lägg till skills för inloggad student
    @PostMapping("/me/add-skills")
    public ResponseEntity<Student> addMySkills(
            @RequestBody List<String> skillNames,
            Authentication auth) {
        try {
            String email = auth.getName();
            Optional<Student> student = studentRepository.findByEmail(email);
            if (student.isPresent()) {
                studentService.addSkillsToStudent(student.get().getId(), skillNames);
                Student updatedStudent = studentService.getStudentById(student.get().getId());
                return ResponseEntity.ok(updatedStudent);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/id/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @DeleteMapping("/{id}/skills/{skillId}")
    public ResponseEntity<String> removeSkillFromStudent(
            @PathVariable Long id,
            @PathVariable Long skillId) {

        studentService.deleteSkillById(id, skillId);
        return ResponseEntity.ok("Skill removed from student " + id);
    }

    @PutMapping("/{id}/cv")
    public ResponseEntity<String> uploadCvForExistingStudent(
            @PathVariable Long id,
            @RequestParam("cv") MultipartFile file
    ) {
        try {
            studentService.updateStudentCv(id, file);
            return ResponseEntity.ok("CV uppladdat!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fel vid uppladdning av CV.");
        }
    }

    @GetMapping("/{id}/skills")
    public ResponseEntity<Set<String>> getStudentSkills(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student.getSkillNames());
    }

    @PostMapping("/{id}/add-skills")
    public ResponseEntity<?> addSkillsToStudent(
            @PathVariable Long id,
            @RequestBody List<String> skillNames) {
        studentService.addSkillsToStudent(id, skillNames);
        Student updatedStudent = studentService.getStudentById(id);
        return ResponseEntity.ok(updatedStudent);
    }

    @PutMapping("/{id}/education")
    public ResponseEntity<Student> updateStudentEducation(
            @PathVariable Long id,
            @RequestBody String education) {

        try {
            Student updatedStudent = studentService.updateEducation(id, education);
            return ResponseEntity.ok(updatedStudent);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}