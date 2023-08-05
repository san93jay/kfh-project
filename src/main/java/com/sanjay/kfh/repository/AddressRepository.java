package com.sanjay.kfh.repository;

import com.sanjay.kfh.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sanjay Vishwakarma
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
}
