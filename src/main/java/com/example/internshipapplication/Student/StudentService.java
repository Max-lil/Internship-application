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

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Integer id) {
        if (id != null) {
            return studentRepository.findById(id);
        }
        return null;
    }

    public boolean emailExists(String email) {
        return studentRepository.existsByEmail(email);
    }

    public void deleteSkillById(Long studentId, Long skillId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student inte hittad"));

        student.getSkills().removeIf(skill -> skill.getId().equals(skillId));

        studentRepository.save(student);
    }

    // Metod för att lägga till en ny student med tillhörande information, CV och färdigheter
    public Student addStudent(String firstName, String lastName, String location,
                              String email, String phoneNumber, MultipartFile file,
                              List<String> skillNames) {
        try {
            // Skapa ett nytt Student-objekt
            Student student = new Student();
            student.setFirstName(firstName);
            student.setLastname(lastName);
            student.setLocation(location);
            student.setEmail(email);
            student.setPhoneNumber(phoneNumber);

            // Hantering av uppladdat CV
            if (file != null && !file.isEmpty()) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename()); // Rensar filnamnet
                String uploadDir = "uploads/cv/";
                File uploadPath = new File(uploadDir);

                // Skapa katalog om den inte redan finns
                if (!uploadPath.exists()) uploadPath.mkdirs();

                // Bestäm sökväg och kopiera filen till servern
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                student.setCvFile(fileName); // Spara filnamnet i studentobjektet
            }

            // Hantering av färdigheter
            if (skillNames != null) {
                Set<Skill> skillSet = new HashSet<>();
                for (String skillName : skillNames) {
                    // Hämta färdighet från databasen eller skapa ny om den inte finns
                    Skill skill = skillRepository.findByNameIgnoreCase(skillName.trim())
                            .orElseGet(() -> skillRepository.save(new Skill(skillName.trim())));
                    skillSet.add(skill);
                }
                student.setSkills(skillSet); // Lägg till färdigheterna i studentobjektet
            }

            // Spara studenten i databasen och returnera objektet
            return studentRepository.save(student);

        } catch (Exception e) {
            // Hantera fel genom att kasta ett undantag
            throw new RuntimeException("Misslyckades att registrera student.", e);
        }
    }

    // Metod för att ladda upp ett nytt CV för en befintlig student
    public void uploadCvForStudent(Long id, MultipartFile file) {
        // Hämta student från databasen, eller kasta fel om inte hittad
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student med ID " + id + " hittades inte"));

        if (file != null && !file.isEmpty()) {
            try {
                // Samma hantering som i addStudent: rensa filnamn, skapa katalog, kopiera fil
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                String uploadDir = "uploads/cv/";
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Spara nya filnamnet och uppdatera studentposten
                student.setCvFile(fileName);
                studentRepository.save(student);

            } catch (Exception e) {
                // Felhantering vid uppladdning
                throw new RuntimeException("Kunde inte ladda upp CV: " + e.getMessage(), e);
            }
        } else {
            // Filen var tom eller saknades
            throw new RuntimeException("Ingen fil angavs.");
        }
    }
}

