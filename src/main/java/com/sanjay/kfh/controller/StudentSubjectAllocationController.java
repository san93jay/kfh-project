package com.sanjay.kfh.controller;

import com.sanjay.kfh.dto.CourseDTO;
import com.sanjay.kfh.dto.StudentDTO;
import com.sanjay.kfh.service.CourseService;
import com.sanjay.kfh.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sanjay Vishwakarma
 */

@RestController
@RequestMapping("/api/studentAllocation")
public class StudentSubjectAllocationController {

     @Autowired
     private CourseService courseService;

     @Autowired
     private StudentCourseService studentService;

     @GetMapping("/getAllCourses")
     public ResponseEntity<List<CourseDTO>> getAllCoursesForStudent(){

          List<CourseDTO> allcourse=courseService.getAllCourseList();
          return  new ResponseEntity<List<CourseDTO>>(allcourse, HttpStatus.OK);
     }

     @PostMapping("/courseAllocation")
     public ResponseEntity<StudentDTO> CourseAllocationWithStudent(@RequestBody StudentDTO studentDTO){

          StudentDTO studentWithCourses=studentService.studentCourseAllocation(studentDTO);
          return  new ResponseEntity<StudentDTO>(studentWithCourses, HttpStatus.OK);
     }

    @GetMapping("/allStudentWithCourses")
    public ResponseEntity<List<StudentDTO>> CourseAllocationWithStudent(){

        List<StudentDTO> studentWithCourses=studentService.allStudentWithTheirCourses();
        return  new ResponseEntity<List<StudentDTO>>(studentWithCourses, HttpStatus.OK);
    }

    @PutMapping("/updateCoursesForStudent")
    public ResponseEntity<StudentDTO> updateCoursesForStudent(@RequestBody StudentDTO studentDTO){

        StudentDTO studentWithUpdatedCourses=studentService.updateCoursesForStudent(studentDTO);
        return  new ResponseEntity<StudentDTO>(studentWithUpdatedCourses, HttpStatus.OK);
    }

    @DeleteMapping("/deleteStudentWithCourses/{id}")
    public ResponseEntity<String> deleteStudentWithCourses(@PathVariable("id") Long studentId){

        String deleteStudentWithCourses=studentService.deleteStudentWithCourses(studentId);
        return  new ResponseEntity<String>(deleteStudentWithCourses, HttpStatus.OK);
    }






}
