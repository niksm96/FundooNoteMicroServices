package com.bridgelabz.collaborator.service;

import com.bridgelabz.collaborator.model.Collaborator;

public interface CollaboratorServiceInf {
	Collaborator addCollabotor(int noteId,String token);
	
	boolean deleteCollaborator(int noteId,String token);
	
}
