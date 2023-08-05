package com.sanjay.kfh.serviceImpl;

import com.sanjay.kfh.dto.CourseDTO;
import com.sanjay.kfh.dto.StudentDTO;
import com.sanjay.kfh.model.Course;
import com.sanjay.kfh.model.Student;
import com.sanjay.kfh.repository.CourseRepository;
import com.sanjay.kfh.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Sanjay Vishwakarma
 */
@Service
@Slf4j
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CourseDTO addCourse(CourseDTO courseDTO) {
        Course course=modelMapper.map(courseDTO, Course.class);
       Course courseDB= courseRepository.save(course);
       if(courseDB!=null){
           courseDTO= modelMapper.map(courseDB, CourseDTO.class);
       }
        return courseDTO;
    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO) {
        Course course=null;
        try{
            if(courseDTO.getCourseId()!=null){
             Optional<Course> courseDb=courseRepository.findById(courseDTO.getCourseId());
             if(courseDb.isPresent()){

                 course=courseDb.get();
                 course.setCourseId(course.getCourseId());
                 course.setCourseDuration(courseDTO.getCourseDuration());
                 course.setCourseName(courseDTO.getCourseName());
                 course.setCourseStartDate(courseDTO.getCourseStartDate());
                 course.setFees(courseDTO.getFees());
                 course=courseRepository.save(course);
                 courseDTO=modelMapper.map(course,CourseDTO.class);
             }
            }else{
                throw  new RuntimeException("Cource Id can not be null");
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return courseDTO;
    }

    @Override
    public List<CourseDTO> getAllCourseList() {
        List<CourseDTO> courseDTOList=new ArrayList<>();
        try{
            List<Course> courseList=courseRepository.findAll();
            if(courseList!=null){
                courseDTOList=converCourseToCourseDTO(courseList);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return courseDTOList;
    }

    private List<CourseDTO> converCourseToCourseDTO(List<Course> courseList) {
         List<CourseDTO> courseDTOList=new ArrayList<>();
        List<StudentDTO> studentDTOS=new ArrayList<>();
        courseList.stream().map(e->courseDTOList.add(new CourseDTO(e.getCourseId(),
                 e.getCourseName(),e.getFees(),e.getCourseDuration(),e.getCourseStartDate()))).collect(Collectors.toList());
        return courseDTOList;
    }

    @Override
    public String deleteCourse(Long id) {
        String deleteMessage=null;
        try{
           Optional<Course> cource=courseRepository.findById(id);
           if(cource.isPresent()){
             courseRepository.deleteById(id);
               deleteMessage= "Cource Deleted Successfully By Id :" + id;
           }else{
               deleteMessage="Cource Not Deleted Successfully By Id :" + id;
           }
        }catch (Exception e){
            log.error(e.getMessage());
        }
         return deleteMessage;
    }
}
