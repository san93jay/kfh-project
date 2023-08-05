package com.sanjay.kfh.repository;


import com.sanjay.kfh.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

/**
 * @author Sanjay Vishwakarma
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {



}
