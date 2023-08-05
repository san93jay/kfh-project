package com.sanjay.kfh.repository;

import com.sanjay.kfh.enums.ERole;
import com.sanjay.kfh.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sanjay Vishwakarma
 */
@Repository
public interface RoleRepository  extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
