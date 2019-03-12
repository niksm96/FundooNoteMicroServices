package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;

public interface NoteService {

	boolean create(Note note, String token);

	List<Note> retrieve(String token);

	Note updateNote(int noteId, Note note, String token);

	boolean deleteNote(int noteId, String token);
	
//	List<Note> retrieveArchivedNotes(String token);

	Label createLabel(Label label, String token);

	List<Label> retrieveLabel(String token);

	Label updateLabel(int labelId, Label label, String token);

	boolean deleteLabel(int labelId, String token);

	boolean addLabelToNote(int noteId, Label label);

	boolean removeLabelFromNote(int noteId, int labelId);

}
