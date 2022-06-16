package com.cg.Faculty_Management_System.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.Faculty_Management_System.model.Faculty;
import com.cg.Faculty_Management_System.repo.FacultyRepo;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class Controller {
	@Autowired
	private FacultyRepo facultyRepository;
	
	// get all faculty
	@GetMapping("/")
	public List<Faculty> getAllFaculty(){
		return facultyRepository.findAll();
	}		
	
	// create faculty rest api
	@PostMapping("/add")
	public Faculty createFaculty(@RequestBody Faculty faculty) {
		return facultyRepository.save(faculty);
	}
		
	// update faculty rest api
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody Faculty facultyDetails) throws Exception{
		Faculty faculty = facultyRepository.findById(id)
				.orElseThrow(()->new Exception("Faculty not exist with id :" + id));
		
		faculty.setFirstName(facultyDetails.getFirstName());
		faculty.setLastName(facultyDetails.getLastName());
		faculty.setEmailId(facultyDetails.getEmailId());
		faculty.setCourse(facultyDetails.getCourse());
		faculty.setPhoneNo(facultyDetails.getPhoneNo());
		faculty.setSalary(facultyDetails.getSalary());
		
		Faculty updatedFaculty = facultyRepository.save(faculty);
		return ResponseEntity.ok(updatedFaculty);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) throws Exception{
		Faculty faculty = facultyRepository.findById(id)
				.orElseThrow(() -> new Exception("Faculty not exist with id :" + id));
		
		facultyRepository.delete(faculty);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
