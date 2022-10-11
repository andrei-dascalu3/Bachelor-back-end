package com.fii.backendapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fii.backendapp.entity.Student;

@RepositoryRestResource(path = "studenti")
public interface StudentRepository extends JpaRepository<Student, Integer>{
	// Interface extending existing methods for usual REST requests
	// To be completed if necessary
	// DONE
}
