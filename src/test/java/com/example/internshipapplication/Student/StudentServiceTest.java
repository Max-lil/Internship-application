package com.example.internshipapplication.Student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentRepository studentRepository;
    private SkillRepository skillRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        skillRepository = mock(SkillRepository.class);
        studentService = new StudentService(studentRepository, skillRepository);
    }

    @Test
    void testGetStudentById() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Test");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));


        Student result = studentService.getStudentById(1L);
        assertNotNull(result);
        assertEquals("Test", result.getFirstName());
    }

    @Test
    void testGetAllStudents() {
        Student student = new Student();
        student.setFirstName("Test");

        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<Student> students = studentService.getAllStudents();
        assertEquals(1, students.size());
        assertEquals("Test", students.get(0).getFirstName());
    }
}
