package com.cg.Faculty_Management_System.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "faculties")
public class Faculty {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "first_name",nullable=false)
	private String firstName;

	@Column(name = "last_name",nullable=false)
	private String lastName;
	
	@Column(name = "email_id",nullable=false)
	private String emailId;
	
	@Column(name = "course",nullable=false)
	private String course;
}
