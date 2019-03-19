package com.bridgelabz.fundoonotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.model.Collaborator;
import com.bridgelabz.fundoonotes.repository.CollaboratorRepository;
import com.bridgelabz.fundoonotes.utility.EmailSender;

@Service
public class CollaboratorServiceImpl implements CollaboratorServiceInf{
	
	@Autowired
	private CollaboratorRepository collaboratorRepository;
	
	@Autowired
	private EmailSender emailSender;
	
	@Override
	public Collaborator addCollabotor(int noteId, int userId) {
		Collaborator collaborator = new Collaborator();
		collaborator.setNoteId(noteId).setUserId(userId);
		Collaborator createdCollaborator= collaboratorRepository.save(collaborator);
		if(createdCollaborator!=null) {
			emailSender.sendEmail("");
			return createdCollaborator;
		}
		return null;
	}

	@Override
	public boolean removeCollaborator(int noteId, int userId) {
		Collaborator existingCollaborator = collaboratorRepository.findByNoteIdAndUserId(noteId, userId).get();
		if(existingCollaborator != null) {
			collaboratorRepository.delete(existingCollaborator);
			return true;
		}
		return false;
	}

}
