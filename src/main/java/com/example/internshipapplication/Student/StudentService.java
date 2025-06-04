package com.example.internshipapplication.Student;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.*;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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

    public Student addStudent(String firstName, String lastName, String location,
                              String email, String phoneNumber, MultipartFile file) {
        try {
            Student student = new Student();
            student.setFirstName(firstName);
            student.setLastname(lastName);
            student.setLocation(location);
            student.setEmail(email);
            student.setPhoneNumber(phoneNumber);

            // ============================
            // CV-UPPLADDNING (OM FIL FINNS)
            // ============================
            if (file != null && !file.isEmpty()) {
                // Sanera filnamnet för säkerhet
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());

                // Ange katalog för CV-filer
                String uploadDir = "uploads/cv/";

                // Skapa katalogen om den inte finns
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) uploadPath.mkdirs();

                // Skapa fullständig sökväg till filen
                Path filePath = Paths.get(uploadDir, fileName);

                // Kopiera innehållet från MultipartFile till filsystemet
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Koppla filnamnet till studentobjektet
                student.setCvFile(fileName);
            }

            return studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Kunde inte spara student och CV: " + e.getMessage(), e);
        }

    }
    /**
     * Laddar upp eller uppdaterar en students CV i efterhand.
     *
     * @param id   Studentens ID
     * @param file Den nya CV-filen (MultipartFile)
     */
    public void uploadCvForStudent(Long id, MultipartFile file) {
        // Hämta student från databasen, annars kasta fel
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student med ID " + id + " hittades inte"));

        // Kontrollera att filen är medskickad och inte tom
        if (file != null && !file.isEmpty()) {
            try {
                // Rensa filnamnet för att undvika skadliga stigar eller tecken
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());

                // Ange sökvägen där CV-filer ska sparas
                String uploadDir = "uploads/cv/";

                // Skapa katalogen om den inte finns
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }

                // Skapa en fullständig filsökväg
                Path filePath = Paths.get(uploadDir, fileName);

                // Kopiera filens innehåll från HTTP-requesten till filsystemet
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Uppdatera studentobjektets cvFile-fält
                student.setCvFile(fileName);

                // Spara den uppdaterade studenten i databasen
                studentRepository.save(student);
            } catch (Exception e) {
                // Om något går fel under uppladdningen – kasta ett generellt fel
                throw new RuntimeException("Kunde inte ladda upp CV: " + e.getMessage(), e);
            }
        } else {
            // Om ingen fil skickades alls
            throw new RuntimeException("Ingen fil angavs.");
        }
    }

}
