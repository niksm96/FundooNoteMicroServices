package com.bridgelabz.collaborator.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.collaborator.model.Collaborator;
import com.bridgelabz.collaborator.repository.CollaboratorRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.utility.TokenGenerator;

public class CollaboratorServiceImpl implements CollaboratorServiceInf{

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private TokenGenerator tokenGenerator;
	
	@Autowired
	private CollaboratorRepository collaboratorRepository;

	
	@Override
	public Collaborator addCollabotor(int noteId, String token) {
		int userId = tokenGenerator.verifyToken(token);
		return null;
	}

	@Override
	public boolean deleteCollaborator(int noteId, String token) {
		// TODO Auto-generated method stub
		return false;
	}

}
