package com.sanjay.kfh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.List;

/**
 * @author Sanjay Vishwakarma
 */
@Entity
@Table(name = "course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column(name="course_name")
    private String courseName;

    @Column(name="fees")
    private int fees;

    @Column(name="course_duration")
    private String courseDuration;

    @Column(name="start_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private String courseStartDate;

    /*@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "student_course", joinColumns = {
            @JoinColumn(name = "COURSE_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "STUDENT_ID", nullable = false, updatable = false) })
    private List<Student> students;*/
}
