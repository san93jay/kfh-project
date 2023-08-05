package com.sanjay.kfh.dto;

import com.sanjay.kfh.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Sanjay Vishwakarma
 */
@Data
@NoArgsConstructor
@Builder
public class CourseDTO {
    private Long courseId;
    private String courseName;
    private int fees;
    private String courseDuration;
    private String courseStartDate;
    private List<StudentDTO> students;

    public CourseDTO(Long courseId, String courseName, int fees, String courseStartDate,String courseDuration,List<StudentDTO> students) {
       this.courseId=courseId;
        this.courseName=courseName;
        this.fees=fees;
        this.courseDuration=courseDuration;
        this.courseStartDate=courseStartDate;
       this.students=students;
    }

    public CourseDTO(Long courseId, String courseName, int fees, String courseStartDate,String courseDuration) {
        this.courseId=courseId;
        this.courseName=courseName;
        this.fees=fees;
        this.courseDuration=courseDuration;
        this.courseStartDate=courseStartDate;
    }
}
