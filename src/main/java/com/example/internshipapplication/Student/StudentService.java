package com.example.internshipapplication.Student;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final SkillRepository skillRepository;

    public StudentService(StudentRepository studentRepository, SkillRepository skillRepository) {
        this.studentRepository = studentRepository;
        this.skillRepository = skillRepository;
    }

    // Hämta alla studenter
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Hämta student via ID (kastar fel om ID saknas eller inte finns)
    public Student getStudentById(Long id) {
        if (id == null) {
            throw new RuntimeException("Student ID får inte vara null");
        }
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student med ID " + id + " hittades inte"));
    }

    // Lägg till nya skills till en befintlig student
    public void addSkillsToStudent(Long studentId, List<String> skillNames) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student med ID " + studentId + " hittades inte"));

        // Om studenten redan har skills, återanvänd dessa, annars starta ny lista
        Set<Skill> currentSkills = student.getSkills() != null ? student.getSkills() : new HashSet<>();

        // Lägg till (eller skapa) varje skill i databasen
        for (String skillName : skillNames) {
            Skill skill = skillRepository.findByNameIgnoreCase(skillName.trim())
                    .orElseGet(() -> skillRepository.save(new Skill(skillName.trim())));
            currentSkills.add(skill);
        }

        // Uppdatera studentens skills
        student.setSkills(currentSkills);
        studentRepository.save(student);
    }

    // Kontrollera om en e-postadress redan finns
    public boolean emailExists(String email) {
        return studentRepository.existsByEmail(email);
    }

    // TODO: här kan du implementera borttagning av en viss skill
    public void deleteSkillById(Long studentId, Long skillId) {
        // ... ej implementerat än
    }

    // Uppdatera utbildning på en student
    public Student updateEducation(Long studentId, String education) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student med ID " + studentId + " hittades inte"));

        student.setEducation(education);
        return studentRepository.save(student);
    }

    // Lägg till en ny student med CV och ev. skills
    public Student addStudent(String firstName, String lastName, String location,
                              String email, String phoneNumber, MultipartFile file,
                              List<String> skillNames, String education, String password) {
        try {
            // Skapa ett nytt Student-objekt
            Student student = new Student();
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setLocation(location);
            student.setEmail(email);
            student.setPhoneNumber(phoneNumber);
            student.setEducation(education);
            student.setPassword(password);

            // Hantera CV om en fil skickats in
            if (file != null && !file.isEmpty()) {
                handleCvUpload(student, file);
            }

            // Hantera skills (frivilligt)
            if (skillNames != null) {
                addSkillsToStudentObject(student, skillNames);
            }

            // Spara i databasen
            return studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Kunde inte lägga till student: " + e.getMessage(), e);
        }
    }

    // Uppdatera CV för en redan befintlig student
    public void updateStudentCv(Long studentId, MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("Ingen fil angavs.");
            }

            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student med ID " + studentId + " hittades inte"));

            // Samma CV-hantering som i addStudent()
            handleCvUpload(student, file);

            // Spara det uppdaterade studentobjektet i databasen
            studentRepository.save(student);

        } catch (Exception e) {
            throw new RuntimeException("Kunde inte ladda upp CV: " + e.getMessage(), e);
        }
    }

    // --- Hjälpmetoder (privata) ---

    /**
     * Säker hantering av CV-uppladdning.
     * - Tillåter endast PDF
     * - Max 10 MB
     * - Ger filen ett slumpmässigt UUID-namn
     * - Lagrar filen under uploads/cv/
     */
    private void handleCvUpload(Student student, MultipartFile file) throws Exception {
        String original = StringUtils.cleanPath(file.getOriginalFilename());

        if (!original.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Endast PDF tillåtet");
        }
        if (file.getSize() > 10 * 1024 * 1024) { // 10 MB
            throw new IllegalArgumentException("Max 10 MB");
        }

        String uploadDir = "uploads/cv/";
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) uploadPath.mkdirs();

        // Skapa ett unikt filnamn
        String safeName = java.util.UUID.randomUUID() + ".pdf";
        Path filePath = Paths.get(uploadDir).resolve(safeName).normalize();

        // Kopiera filen till rätt plats
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Spara filnamnet på studentobjektet
        student.setCvFile(safeName);
    }

    /**
     * Hjälpmetod för att lägga till skills till en ny student
     */
    private void addSkillsToStudentObject(Student student, List<String> skillNames) {
        Set<Skill> skills = student.getSkills() != null ? student.getSkills() : new HashSet<>();

        for (String skillName : skillNames) {
            Skill skill = skillRepository.findByNameIgnoreCase(skillName.trim())
                    .orElseGet(() -> skillRepository.save(new Skill(skillName.trim())));
            skills.add(skill);
        }

        student.setSkills(skills);
    }
}
