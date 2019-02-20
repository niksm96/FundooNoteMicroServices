package com.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.utility.TokenGenerator;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private TokenGenerator tokenGenerator;

	@Override
	public boolean create(Note note, String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		note.setUserId(userId);
		Note registeredNote = noteRepository.save(note);
		return (registeredNote != null) ? true : false;
	}

	@Override
	public List<Note> retrieve(String token, HttpServletRequest request) {
		List<Note> notes = null;
		int userId = tokenGenerator.verifyToken(token);
		notes = noteRepository.findAllByUserId(userId);
		System.out.println(notes);
		return notes;
	}

	@Override
	public Note updateNote(int noteId, Note note, String token, HttpServletRequest request) {
		Note newNote = null;
		int userId = tokenGenerator.verifyToken(token);
		List<Note> notes = noteRepository.findAllByUserId(userId);
		if (!notes.isEmpty()) {
			newNote = notes.stream().filter(exitingNote -> exitingNote.getNoteId() == noteId).findAny().get();
			newNote.setTitle(note.getTitle());
			newNote.setDescription(note.getDescription());
			newNote.setArchive(note.isArchive());
			newNote.setPinned(note.isPinned());
			newNote.setTrashed(note.isTrashed());
			noteRepository.save(newNote);
		}
		return newNote;
	}

	@Override
	public boolean deleteNote(int noteId, String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		List<Note> notes = noteRepository.findAllByUserId(userId);
		if (!notes.isEmpty()) {
			Note existingNote = notes.stream().filter(exitingNote -> exitingNote.getNoteId() == noteId).findAny().get();
			noteRepository.delete(existingNote);
			return true;
		}
		return false;
	}

	@Override
	public boolean createLabel(Label label, String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		label.setUserId(userId);
		Label createdLabel = labelRepository.save(label);
		return (createdLabel != null) ? true : false;
	}

	@Override
	public List<Label> retrieveLabel(String token, HttpServletRequest request) {
		List<Label> labels = null;
		int userId = tokenGenerator.verifyToken(token);
		labels = labelRepository.findAllByUserId(userId);
		return labels;
	}

	@Override
	public Label updateLabel(int labelId, Label label, String token, HttpServletRequest request) {
		Label newLabel = null;
		int userId = tokenGenerator.verifyToken(token);
		List<Label> labels = labelRepository.findAllByUserId(userId);
		if (!labels.isEmpty()) {
			newLabel = labels.stream().filter(exitingLabel -> exitingLabel.getLabelId() == labelId).findAny().get();
			newLabel.setLabelName(label.getLabelName());
			labelRepository.save(newLabel);
		}
		return newLabel;
	}

	@Override
	public boolean deleteLabel(int labelId, String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		List<Label> labels = labelRepository.findAllByUserId(userId);
		if (!labels.isEmpty()) {
			Label existingLabel = labels.stream().filter(exitingLabel -> exitingLabel.getLabelId() == labelId).findAny().get();
			labelRepository.delete(existingLabel);
			return true;
		}
		return false;
	}

	@Override
	public boolean addLabelToNote(int noteId, int labelId, HttpServletRequest request) {
		Note note = noteRepository.findByNoteId(noteId);
		Label label = labelRepository.findByLabelId(labelId);
		List<Label> labels = note.getListOfLabels();
		labels.add(label);
		if (!labels.isEmpty()) {
			note.setListOfLabels(labels);
			noteRepository.save(note);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeLabelFromNote(int noteId, int labelId, HttpServletRequest request) {
		Note note = noteRepository.findByNoteId(noteId);
		List<Label> labels = note.getListOfLabels();
		if (!labels.isEmpty()) {
			labels = labels.stream().filter(label -> label.getLabelId() != labelId).collect(Collectors.toList());
			note.setListOfLabels(labels);
			noteRepository.save(note);
			return true;
		}
		return false;
	}

}
