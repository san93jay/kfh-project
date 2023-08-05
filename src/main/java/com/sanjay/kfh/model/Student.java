package com.sanjay.kfh.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;


/**
 * @author Sanjay Vishwakarma
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "student",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="full_name")
    @Pattern(regexp = "^[\u0621-\u064A\u0660-\u0669a-zA-Z\\-_\\s]{6,12}$", message = "Name Full name in English and Arabic Only")
    private String name;
    private String username;
    private String email;
    private String password;
    private int age;
    private String contactNumber;

    @OneToOne
    private Role roles;

    @OneToOne
    private Address address;

  /*  @Column(name="isDeleted", updatable = true,columnDefinition="boolean default false")
    private Boolean isDeleted;*/

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private List<Course> courses;

    public Student(String name, String username, String email, String password, int age, String contactNumber,Address address) {
           this.name=name;
           this.username=username;
           this.email=email;
           this.password=password;
           this.age=age;
           this.contactNumber=contactNumber;
           this.address=address;
    }
}
