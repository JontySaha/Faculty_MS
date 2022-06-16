package com.cg.Faculty_Management_System.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.Faculty_Management_System.model.Faculty;
import com.cg.Faculty_Management_System.model.Role;
import com.cg.Faculty_Management_System.model.User;
import com.cg.Faculty_Management_System.payload.LoginDto;
import com.cg.Faculty_Management_System.payload.SignUpDto;
import com.cg.Faculty_Management_System.repo.FacultyRepo;
import com.cg.Faculty_Management_System.repo.RoleRepo;
import com.cg.Faculty_Management_System.repo.UserRepo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepository;
    
    @Autowired
	private FacultyRepo facultyRepository;

    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
    
 // get all faculty
 	@GetMapping("/admin/view")
 	public List<Faculty> getAllFaculty(){
 		return facultyRepository.findAll();
 	}		
 	
 	// create faculty rest api
 	@PostMapping("/admin/add")
 	public Faculty createFaculty(@RequestBody Faculty faculty) {
 		return facultyRepository.save(faculty);
 	}
 		
 	// update faculty rest api
 	
 	@PutMapping("/admin/edit/{id}")
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
 	
 	@DeleteMapping("/admin/delete/{id}")
 	public ResponseEntity<Map<String, Boolean>> deleteFaculty(@PathVariable Long id) throws Exception{
 		Faculty faculty = facultyRepository.findById(id)
 				.orElseThrow(() -> new Exception("Faculty not exist with id :" + id));
 		
 		facultyRepository.delete(faculty);
 		Map<String, Boolean> response = new HashMap<>();
 		response.put("deleted", Boolean.TRUE);
 		return ResponseEntity.ok(response);
 	}
}
