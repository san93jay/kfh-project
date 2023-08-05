package com.sanjay.kfh.service;

import com.sanjay.kfh.dto.CourseDTO;

import java.util.List;

/**
 * @author Sanjay Vishwakarma
 */
public interface CourseService {
    CourseDTO addCourse(CourseDTO courseDTO);
    CourseDTO updateCourse(CourseDTO courseDTO);
    List<CourseDTO> getAllCourseList();
    String deleteCourse(Long id);

}
