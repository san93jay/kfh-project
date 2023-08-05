package com.sanjay.kfh.controller;

import com.sanjay.kfh.dto.RoleDTO;
import com.sanjay.kfh.enums.ERole;
import com.sanjay.kfh.exception.InvalidRoleException;
import com.sanjay.kfh.model.Role;
import com.sanjay.kfh.model.Student;
import com.sanjay.kfh.repository.RoleRepository;
import com.sanjay.kfh.utils.KfhConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Sanjay Vishwakarma
 */
@RestController
@RequestMapping("/api/student/role")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/add")
    public ResponseEntity<?> registerUser(@RequestBody RoleDTO roleDto) {
        Role roles = new Role();
        Set<String> strRoles = new HashSet<>();
        strRoles.add(roleDto.getName());
        if (roleDto == null) {
            throw new InvalidRoleException(KfhConstant.INVALID_ROLE, "Role will not be null",null);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Optional<Role> admin = roleRepository.findByName(ERole.ROLE_ADMIN);
                        if (admin.isPresent()) {
                            throw new InvalidRoleException(KfhConstant.INVALID_ROLE, HttpStatus.BAD_REQUEST.name(),null);
                        }else{
                            roles.setName(ERole.ROLE_ADMIN);
                        }
                        break;
                    default:
                        Optional<Role> RoleStudent = roleRepository.findByName(ERole.ROLE_USER);
                        if (RoleStudent.isPresent()) {
                            throw new InvalidRoleException(KfhConstant.INVALID_ROLE, HttpStatus.BAD_REQUEST.name(),null);
                        }else {
                            roles.setName(ERole.ROLE_USER);
                        }
                }
            });

            roleRepository.save(roles);

            return ResponseEntity.ok("Role registered successfully!");
        }
    }
}
