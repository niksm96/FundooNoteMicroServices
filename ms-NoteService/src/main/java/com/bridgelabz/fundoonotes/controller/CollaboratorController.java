package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bridgelabz.fundoonotes.service.CollaboratorServiceInf;

@Controller
@RequestMapping("/collaborator")
public class CollaboratorController {

	public static final String USER_URI = "http://localhost:8080/users";
	@Autowired
	CollaboratorServiceInf collaboratorService;

	@PostMapping(value = "/addcollaborator/{noteId}/{userId}")
	public ResponseEntity<?> addCollaborator(@PathVariable("noteId") int noteId, @PathVariable("userId") int userId) {
		if (collaboratorService.addCollabotor(noteId, userId) != null)
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("Cannot add collaborator", HttpStatus.CONFLICT);
	}
	
	@DeleteMapping(value = "/removecollaborator/{noteId}/{userId}")
	public ResponseEntity<?> removeCollaborator(@PathVariable("noteId") int noteId, @PathVariable("userId") int userId) {
		if (collaboratorService.removeCollaborator(noteId, userId))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("Cannot add collaborator", HttpStatus.CONFLICT);
	}

}
