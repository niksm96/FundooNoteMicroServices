package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.model.Collaborator;

public interface CollaboratorServiceInf {
	Collaborator addCollabotor(int noteId,int userId);
	
	boolean removeCollaborator(int noteId,int userId);
	
}
