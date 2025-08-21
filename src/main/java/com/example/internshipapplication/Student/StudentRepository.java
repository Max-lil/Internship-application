package com.example.internshipapplication.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // LÄGG TILL DENNA - behövs för UserService
    Optional<Student> findByEmail(String email);

    // Kolla dubbletter av e-post
    boolean existsByEmail(String email);
}


