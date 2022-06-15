package com.cg.Faculty_Management_System.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.Faculty_Management_System.model.Faculty;

@Repository
public interface FacultyRepo extends JpaRepository<Faculty,Long>{

}