package com.bridgelabz.fundoonotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.model.Collaborator;
import com.bridgelabz.fundoonotes.repository.CollaboratorRepository;


@Service
public class CollaboratorServiceImpl implements CollaboratorServiceInf{
	
	@Autowired
	private CollaboratorRepository collaboratorRepository;

	
	@Override
	public Collaborator addCollabotor(int noteId, int userId) {
		Collaborator collaborator = new Collaborator();
		collaborator.setNoteId(noteId).setUserId(userId);
		return collaboratorRepository.save(collaborator);
	}

	@Override
	public boolean deleteCollaborator(int noteId, String token) {
		// TODO Auto-generated method stub
		return false;
	}

}
