package com.sanjay.kfh.service;

import com.sanjay.kfh.dto.StudentDTO;
import com.sanjay.kfh.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Sanjay Vishwakarma
 */
public interface StudentCourseService {
    StudentDTO studentCourseAllocation(StudentDTO studentDTO);
    List<StudentDTO> allStudentWithTheirCourses();

    StudentDTO updateCoursesForStudent(StudentDTO studentDTO);

    String deleteStudentWithCourses(Long studentId);



}
