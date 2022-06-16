package com.cg.Faculty_Management_System.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.Faculty_Management_System.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
