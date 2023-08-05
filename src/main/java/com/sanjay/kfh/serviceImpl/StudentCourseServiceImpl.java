package com.sanjay.kfh.serviceImpl;

import com.sanjay.kfh.dto.CourseDTO;
import com.sanjay.kfh.dto.StudentDTO;
import com.sanjay.kfh.exception.StudentCourseIllegalStateException;
import com.sanjay.kfh.exception.StudentIdNotFoundException;
import com.sanjay.kfh.model.Course;
import com.sanjay.kfh.model.Student;
import com.sanjay.kfh.repository.CourseRepository;
import com.sanjay.kfh.repository.StudentRepository;
import com.sanjay.kfh.service.StudentCourseService;
import com.sanjay.kfh.utils.KfhConstant;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class StudentCourseServiceImpl implements StudentCourseService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CourseRepository courseRepository;
    @Override
    public StudentDTO studentCourseAllocation(StudentDTO studentDTO) {
        List<Course> courseList=null;

         try{

             Optional<Student> studentOptional = studentRepository.findById(studentDTO.getId());
             if (!studentOptional.isPresent()) {
                 throw new StudentCourseIllegalStateException(HttpStatus.BAD_REQUEST.value(),KfhConstant.STUDENT_COURSE_ILLEGAL_STATE +""+ + studentDTO.getId(),null);
             }
             Student student = studentOptional.get();
             courseList=studentDTO.getCourses().stream().map(course-> modelMapper.map(course, Course.class)).collect(Collectors.toList());
             student.setCourses(courseList);
             studentDTO.getCourses().stream().map(e->courseRepository.save(modelMapper.map(e,Course.class)));
             studentRepository.save(student);

         }catch (Exception e){
             log.error(e.getMessage());
         }
        return studentDTO;
    }

    @Override
    public List<StudentDTO> allStudentWithTheirCourses() {
        List<StudentDTO> studentDTOList=new ArrayList<>();
        try{
            List<Student> studentDB=studentRepository.findAll();
            if(studentDB!=null){
                studentDTOList=converStudentToStudentDTO(studentDB);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return studentDTOList;
    }

    private List<StudentDTO> converStudentToStudentDTO(List<Student> studentDB) {
        List<CourseDTO> courseDTOList=new ArrayList<>();
        List<StudentDTO> studentDTOS=new ArrayList<>();
        studentDB.stream().map(e->studentDTOS.add(new StudentDTO(e.getId(),e.getName(),e.getUsername(),
                e.getEmail(),e.getContactNumber(),e.getAge(),e.getAddress(),e.getRoles().getName().name(),e.getCourses().stream().map(c->
                        new CourseDTO(c.getCourseId(),c.getCourseName(),c.getFees(),c.getCourseStartDate(),c.getCourseDuration())
                        ).collect(Collectors.toList())))).collect(Collectors.toList());
        return studentDTOS;
    }

    @Override
    public StudentDTO updateCoursesForStudent(StudentDTO studentDTO) {
        Optional<Student> studentDTOListUpdateValue=null;
        List<StudentDTO> studentDtoList=new ArrayList<>();
        StudentDTO studentDto=new StudentDTO();
        try{
            if(studentDTO.getId()!=null){
                Optional<Student> coursesDB=studentRepository.findById(studentDTO.getId());
                if(coursesDB.isPresent()){
                    Student courses=coursesDB.get();
                    studentDTO.getCourses().stream().forEachOrdered(
                            c->{
                                courses.getCourses().stream().forEach(s->
                                            {   if(c.getCourseId()==s.getCourseId()){
                                                s.setCourseId(s.getCourseId());
                                                s.setCourseName(c.getCourseName());
                                                s.setFees(c.getFees());
                                                s.setCourseDuration(c.getCourseDuration());
                                                s.setCourseStartDate(c.getCourseStartDate());
                                                courseRepository.save(s);
                                            }
                                        }
                                        );
                            }
                    );
                   studentDTOListUpdateValue=studentRepository.findById(studentDTO.getId());
                   if(studentDTOListUpdateValue.isPresent()){
                       Student studentUpdated=studentDTOListUpdateValue.get();
                       List<Student> studentsDB=new ArrayList<>();
                       studentsDB.add(studentUpdated);
                       studentDtoList= converStudentToStudentDTO(studentsDB);
                       studentDto=studentDtoList.get(0);
                   }
                }
            }else{
                throw  new RuntimeException("Student Id can not be empty !");
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return studentDto;
    }

    @Override
    public String deleteStudentWithCourses(Long studentId) {
        try{
            if(studentId!=null){
                Optional<Student> studentDb=studentRepository.findById(studentId);
                if(studentDb.isPresent()){
                    studentRepository.deleteById(studentId);
                  /*    Student student=studentDb.get();
                      student.getCourses().stream().forEachOrdered(
                              c->{
                                Optional<Course> course=courseRepository.findById(c.getCourseId());
                                if(course.isPresent()){
                                    Course courseDB=course.get();
                                    courseDB.setCourseId(courseDB.getCourseId());
                                    courseDB.setCourseName(courseDB.getCourseName());
                                    courseDB.setFees(courseDB.getFees());
                                    courseDB.setCourseDuration(courseDB.getCourseDuration());
                                    courseDB.setCourseStartDate(courseDB.getCourseStartDate());
                                    courseRepository.save(courseDB);
                                }
                                studentRepository.deleteById(studentId);
                              }
                      );*/
                      return "Student Deleted successfully" +studentId;
                }else{
                    throw new StudentIdNotFoundException(HttpStatus.BAD_REQUEST.value(), "Please send correct StudentId :" + studentId, null);
                }
            }else{
                throw new StudentIdNotFoundException(HttpStatus.BAD_REQUEST.value(), "Student Is can not be empty ! :" + studentId, null);
            }

        }catch (Exception e){
            log.error(e.getMessage());
        }

        return null;
    }
}
