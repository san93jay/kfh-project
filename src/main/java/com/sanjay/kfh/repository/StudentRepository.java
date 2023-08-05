package com.sanjay.kfh.repository;

import com.sanjay.kfh.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sanjay Vishwakarma
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
    Optional<Student> findByUsername(String username) throws UsernameNotFoundException;
}
