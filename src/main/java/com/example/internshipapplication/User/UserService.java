package com.example.internshipapplication.User;

import com.example.internshipapplication.Company.Company;
import com.example.internshipapplication.Company.CompanyRepository;
import com.example.internshipapplication.Student.Student;
import com.example.internshipapplication.Student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public UserService(StudentRepository studentRepository, CompanyRepository companyRepository) {
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Student roll
        Student student = studentRepository.findByEmail(email).orElse(null);
        if (student != null) {
            return new org.springframework.security.core.userdetails.User(
                    student.getEmail(),
                    student.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_STUDENT"))
            );
        }

        //Company roll
        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user with email" + email));

        return new org.springframework.security.core.userdetails.User(
                company.getEmail(),
                company.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_COMPANY"))
        );


        // Om du saknar roll i entiteten: ge en default-roll
        /*return new org.springframework.security.core.userdetails.User(
                student.getEmail(),
                student.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );*/
    }
}
