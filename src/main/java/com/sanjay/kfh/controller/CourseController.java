package com.sanjay.kfh.controller;

import com.sanjay.kfh.dto.CourseDTO;
import com.sanjay.kfh.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sanjay Vishwakarma
 */

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;


    @PostMapping("/addCourse")
    public ResponseEntity<CourseDTO> addCourse(@RequestBody CourseDTO courseDTO){

        CourseDTO courseDTODB=courseService.addCourse(courseDTO);
        return  new ResponseEntity<CourseDTO>(courseDTODB, HttpStatus.OK);
    }

    @PutMapping("/updateCourse")
    public ResponseEntity<CourseDTO> updateCourse(@RequestBody CourseDTO courseDTO){

        CourseDTO courseDTODB=courseService.updateCourse(courseDTO);
        return  new ResponseEntity<CourseDTO>(courseDTODB, HttpStatus.OK);
    }

    @GetMapping("/getAllCourses")
    public ResponseEntity<List<CourseDTO>> getAllCourses(){

        List<CourseDTO> allcourse=courseService.getAllCourseList();
        return  new ResponseEntity<List<CourseDTO>>(allcourse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteCourseById(@PathVariable("id") Long id){
       String deleteCourseById=courseService.deleteCourse(id);
       if(deleteCourseById!=null){
           return  new ResponseEntity<String>(deleteCourseById, HttpStatus.OK);
       }else{
           return  new ResponseEntity<String>(deleteCourseById, HttpStatus.BAD_REQUEST);
       }
    }


}
