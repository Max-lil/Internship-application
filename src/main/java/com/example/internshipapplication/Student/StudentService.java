package com.example.internshipapplication.Student;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Student getStudentById(Integer id) {
        if(id != null) {
            return studentRepository.findById(id);
        }
        return null;
    }

    public Student addStudent(Student student) {
        if(student != null) {
            return studentRepository.save(student);
        }
        return null;
    }
}
