package com.bridgelabz.collaborator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.collaborator.model.Collaborator;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Integer> {
	

}
