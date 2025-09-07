package com.example.internshipapplication.User;
import com.example.internshipapplication.Company.Company;
import com.example.internshipapplication.Company.CompanyRepository;
import com.example.internshipapplication.Student.Student;
import com.example.internshipapplication.Student.StudentRepository;
import com.example.internshipapplication.Student.StudentService;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void testLoadUserByUsernameStudent() {

        Student student = new Student();

        student.setEmail("teststudent@gmail.com");
        student.setPassword("123456");
        student.setFirstName("Test");
        student.setLastName("Student");
        student.setLocation("Lund");
        student.setPhoneNumber("0701234567");

        studentRepository.save(student);

        UserDetails result = userService.loadUserByUsername("teststudent@gmail.com");

        assertNotNull(result);
        assertEquals("teststudent@gmail.com", result.getUsername());
        assertEquals("123456", result.getPassword());

        assertTrue(result.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT")));

    }

    @Test
    public void testLoadUserByUsernameCompany() {
        Company company = new Company();

        company.setEmail("testcompany@gmail.com");
        company.setPassword("123456");
        company.setName("Test");
        company.setLocation("Lund");

        companyRepository.save(company);

        UserDetails result = userService.loadUserByUsername("testcompany@gmail.com");

        assertNotNull(result);
        assertEquals("testcompany@gmail.com", result.getUsername());
        assertEquals("123456", result.getPassword());

        assertTrue(result.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_COMPANY")));
    }




}
