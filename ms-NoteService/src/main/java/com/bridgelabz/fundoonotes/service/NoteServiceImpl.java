package com.bridgelabz.fundoonotes.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.model.Collaborator;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.repository.CollaboratorRepository;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.utility.TokenGenerator;

@Service
public class NoteServiceImpl implements NoteService {

	private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

	public static final String uploadDirectory = System.getProperty("user.dir") + "/images";

	@Autowired
	private CollaboratorRepository collaboratorRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private TokenGenerator tokenGenerator;

	@Override
	public boolean create(Note note, String token) {
		note.setUserId(tokenGenerator.verifyToken(token));
		Note createdNote = noteRepository.save(note);
		return (createdNote != null) ? true : false;
	}

	@Override
	public List<Note> retrieve(String token) {
		int userId = tokenGenerator.verifyToken(token);
		List<Note> notes = new ArrayList<Note>();
		List<Collaborator> collaborators = collaboratorRepository.findAllByUserId(userId);
		for (Collaborator collaborator : collaborators) {
			notes.add(noteRepository.findById(collaborator.getNoteId()).get());
		}
		notes.addAll(noteRepository.findAllByUserId(userId));
		return notes;
	}

	@Override
	public Note updateNote(int noteId, Note note, String token) {
//		int userId = tokenGenerator.verifyToken(token);
		Optional<Note> maybeNote = noteRepository.findById(noteId);
		return maybeNote
				.map(existingNote -> noteRepository.save(existingNote.setTitle(note.getTitle())
						.setDescription(note.getDescription()).setArchive(note.isArchive()).setPinned(note.isPinned())
						.setTrashed(note.isTrashed()).setColor(note.getColor()).setReminder(note.getReminder())))
				.orElseGet(() -> null);
	}

	@Override
	@Transactional
	public boolean deleteNote(int noteId, String token) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<Note> maybeNote = noteRepository.findByUserIdAndNoteId(userId, noteId);
		return maybeNote.map(toBeDeletedNote -> {
			noteRepository.delete(toBeDeletedNote);
			return true;
		}).orElseGet(() -> false);
	}

	@Override
	public Label createLabel(Label label, String token) {
		label.setUserId(tokenGenerator.verifyToken(token));
		return labelRepository.save(label);
	}

	@Override
	public List<Label> retrieveLabel(String token) {
		return labelRepository.findAllByUserId(tokenGenerator.verifyToken(token));
	}

	@Override
	public Label updateLabel(int labelId, Label label, String token) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<Label> maybeLabel = labelRepository.findByUserIdAndLabelId(userId, labelId);
		return maybeLabel.map(existingLabel -> labelRepository.save(existingLabel.setLabelName(label.getLabelName())))
				.orElseGet(() -> null);
	}

	@Override
	public boolean deleteLabel(int labelId, String token) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<Label> maybeLabel = labelRepository.findByUserIdAndLabelId(userId, labelId);
		return maybeLabel.map(existingLabel -> {
			labelRepository.delete(existingLabel);
			return true;
		}).orElseGet(() -> false);
	}

	@Override
	public boolean addLabelToNote(int noteId, Label oldLabel) {
		Note note = noteRepository.findByNoteId(noteId);
		Label label = labelRepository.findByLabelId(oldLabel.getLabelId());
		List<Label> labels = note.getListOfLabels();
		labels.add(label);
		if (!labels.isEmpty() && note != null && label != null) {
			note.setListOfLabels(labels);
			noteRepository.save(note);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeLabelFromNote(int noteId, int labelId) {
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

	@Override
	public boolean addImages(MultipartFile[] files, int noteId,String token) throws IOException {
		StringBuilder fileNames = new StringBuilder();
		for (MultipartFile file : files) {
//			Path filePathAndName = Paths.get(uploadDirectory,file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename());
//			Files.write(filePathAndName, file.getBytes());
			File mainFile = new File(uploadDirectory + file.getOriginalFilename());
			file.transferTo(mainFile);
		}
		if(fileNames!=null) {
			int userId = tokenGenerator.verifyToken(token);
			Note note = noteRepository.findByUserIdAndNoteId(userId, noteId).get();
			note.setFileNames(fileNames);
			noteRepository.save(note);
			return true;	
		}
		return false;
	}

}
