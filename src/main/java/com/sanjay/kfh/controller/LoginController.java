package com.sanjay.kfh.controller;

import com.sanjay.kfh.dto.StudentDTO;
import com.sanjay.kfh.enums.ERole;
import com.sanjay.kfh.model.Address;
import com.sanjay.kfh.model.Role;
import com.sanjay.kfh.model.Student;
import com.sanjay.kfh.repository.AddressRepository;
import com.sanjay.kfh.repository.RoleRepository;
import com.sanjay.kfh.repository.StudentRepository;
import com.sanjay.kfh.request.StudentLoginRequest;
import com.sanjay.kfh.responce.KfhJwtResponse;
import com.sanjay.kfh.security.KfhJwtToken;
import com.sanjay.kfh.serviceImpl.KfhStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Sanjay Vishwakarma
 */

@RestController
@RequestMapping("/api/student/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private KfhJwtToken kfhJwtToken;

    @Autowired
    private KfhStudentService kfhStudentService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AddressRepository addressRepository;


    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody StudentLoginRequest studentLoginRequest) throws Exception {

        authenticate(studentLoginRequest.getUsername(), studentLoginRequest.getPassword());

        final UserDetails kfhStudentDetails = kfhStudentService
                .loadUserByUsername(studentLoginRequest.getUsername());

        final String token = kfhJwtToken.generateToken(kfhStudentDetails);

        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("STUDENT_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

        @PostMapping("/signup")
        public ResponseEntity<?> registerUser(@RequestBody StudentDTO studentDTO) {
            Set<String> strRoles = new HashSet<>();
            if (studentRepository.existsByUsername(studentDTO.getUsername())) {
                return ResponseEntity.badRequest().body("Username is already use!");
            }

            if (studentRepository.existsByEmail(studentDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email is already in use!");
            }

            // Create new student's account
            Student student = new Student(studentDTO.getName(),studentDTO.getUsername(),
                    studentDTO.getEmail(),
                    passwordEncoder.encode(studentDTO.getPassword()),
                    studentDTO.getAge(),studentDTO.getContactNumber(),studentDTO.getAddress());

            String studenRole=studentDTO.getRole();
            strRoles.add(studenRole);
            
            Role roles = new Role();

            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.setName(userRole.getName());
                roles.setId(userRole.getId());
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.setName(adminRole.getName());
                            roles.setId(adminRole.getId());

                            break;
                        default:
                            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.setName(userRole.getName());
                            roles.setId(userRole.getId());
                    }
                });
            }

            student.setRoles(roles);
            Address address=addressRepository.save(studentDTO.getAddress());
            student.setAddress(address);
            studentRepository.save(student);

            return ResponseEntity.ok("User registered successfully!");
        }


}
