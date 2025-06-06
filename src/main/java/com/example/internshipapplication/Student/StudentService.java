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

    public Student addStudent(String firstName, String lastName, String location,
                              String email, String phoneNumber, MultipartFile file,
                              List<String> skillNames) {
        try {
            Student student = new Student();
            student.setFirstName(firstName);
            student.setLastname(lastName);
            student.setLocation(location);
            student.setEmail(email);
            student.setPhoneNumber(phoneNumber);

            if (file != null && !file.isEmpty()) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                String uploadDir = "uploads/cv/";
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) uploadPath.mkdirs();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                student.setCvFile(fileName);
            }
            if (skillNames != null) {
                Set<Skill> skillSet = new HashSet<>();
                for (String skillName : skillNames) {
                    Skill skill = skillRepository.findByNameIgnoreCase(skillName.trim())
                            .orElseGet(() -> skillRepository.save(new Skill(skillName.trim())));
                    skillSet.add(skill);
                }
                student.setSkills(skillSet);
            }

            return studentRepository.save(student);

        } catch (Exception e) {
            throw new RuntimeException("Misslyckades att registrera student.", e);
        }
    }

    public void uploadCvForStudent(Long id, MultipartFile file) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student med ID " + id + " hittades inte"));

        if (file != null && !file.isEmpty()) {
            try {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                String uploadDir = "uploads/cv/";
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                student.setCvFile(fileName);
                studentRepository.save(student);
            } catch (Exception e) {
                throw new RuntimeException("Kunde inte ladda upp CV: " + e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("Ingen fil angavs.");
        }
    }
}
