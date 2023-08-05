package com.sanjay.kfh.dto;

import com.sanjay.kfh.model.Address;
import com.sanjay.kfh.model.Course;
import com.sanjay.kfh.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Sanjay Vishwakarma
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private int age;
    private String contactNumber;
    private String role;
    private Address address;
    private List<CourseDTO> courses;

    public StudentDTO(Long id, String name, String username, String email, String contactNumber, int age, Address address, String roles) {
        this.id=id;
        this.name=name;
        this.username=username;
        this.email=email;
        this.contactNumber=contactNumber;
        this.age=age;
        this.role=roles;
        this.address=address;
    }

    public StudentDTO(Long id, String name, String username, String email, String contactNumber, int age, Address address, String roles, List<CourseDTO> courses) {
        this.id=id;
        this.name=name;
        this.username=username;
        this.email=email;
        this.contactNumber=contactNumber;
        this.age=age;
        this.role=roles;
        this.address=address;
        this.courses=courses;
    }
}
