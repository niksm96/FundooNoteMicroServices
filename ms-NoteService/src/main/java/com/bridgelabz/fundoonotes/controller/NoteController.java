package com.bridgelabz.fundoonotes.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.service.NoteService;

@Controller
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteService noteService;

	private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

	@PostMapping(value = "/createnote")
	public ResponseEntity<?> createNote(@RequestBody Note note, @RequestHeader(value = "token") String token) {
		try {
			if (noteService.create(note, token))
				return new ResponseEntity<Void>(HttpStatus.OK);
			return new ResponseEntity<String>("Note creation failed", HttpStatus.CONFLICT);

		} catch (Exception e) {
			logger.error("Error occured", e);
			return new ResponseEntity<String>("Note creation failed", HttpStatus.CONFLICT);
		}

	}

	@GetMapping(value = "/retrievenote")
	public ResponseEntity<?> retrieveNote(@RequestHeader(value = "token", required = false) String token) {
		List<Note> notes = noteService.retrieve(token);
		if (!notes.isEmpty())
			return new ResponseEntity<List<Note>>(notes, HttpStatus.OK);
		else
			return new ResponseEntity<String>("No notes to fetch", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PutMapping(value = "/updatenote/{noteId:.+}")
	public ResponseEntity<?> updateNote(@PathVariable("noteId") int noteId, @RequestBody Note note,
			@RequestHeader(value = "token") String token) {
		Note updatedNote = noteService.updateNote(noteId, note, token);
		if (updatedNote != null)
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("Couldn't update", HttpStatus.CONFLICT);
	}

	@DeleteMapping(value = "/deletenote/{noteId:.+}")
	public ResponseEntity<?> delete(@PathVariable("noteId") int noteId, @RequestHeader(value = "token") String token) {
		try {
			if (!noteService.deleteNote(noteId, token)) {
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error occured", e);
			return new ResponseEntity<String>("Couldn't delete", HttpStatus.CONFLICT);
		}
	}

	@PostMapping(value = "/createlabel")
	public ResponseEntity<?> createLabel(@RequestBody Label label, @RequestHeader(value = "token") String token) {
		try {
			Label createdLabel = noteService.createLabel(label, token);
			if (createdLabel != null)
				return new ResponseEntity<Label>(createdLabel, HttpStatus.OK);
			return new ResponseEntity<String>("Label creation unsuccessful", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error("Error occured", e);
			return new ResponseEntity<String>("Label creation unsuccessful", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/retrievelabel")
	public ResponseEntity<?> retrieveLabel(@RequestHeader(value = "token") String token) {
		List<Label> labels = noteService.retrieveLabel(token);
		if (!labels.isEmpty())
			return new ResponseEntity<List<Label>>(labels, HttpStatus.OK);
		else
			return new ResponseEntity<String>("No labels to fetch", HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "/updatelabel/{labelId:.+}")
	public ResponseEntity<?> updateLabel(@PathVariable("labelId") int labelId, @RequestBody Label label,
			@RequestHeader(value = "token") String token) {
		Label updatedLabel = noteService.updateLabel(labelId, label, token);
		if (updatedLabel != null) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't update", HttpStatus.CONFLICT);
	}

	@DeleteMapping(value = "/deletelabel/{labelId:.+}")
	public ResponseEntity<?> deleteLabel(@PathVariable("labelId") int labelId,
			@RequestHeader(value = "token") String token) {
		try {
			if (!noteService.deleteLabel(labelId, token)) {
				return new ResponseEntity<String>("Note Not found", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Couldn't delete", HttpStatus.CONFLICT);
		}
	}

	@PutMapping(value = "/addlabeltonote/{noteId:.+}")
	public ResponseEntity<?> addLabelToNote(@PathVariable("noteId") int noteId, @RequestBody Label label) {
		boolean result = noteService.addLabelToNote(noteId, label);
		if (result) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Label could'nt be added to note", HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping(value = "/removelabelfromnote")
	public ResponseEntity<?> removeLabelFromNote(@RequestParam("noteId") int noteId,
			@RequestParam("labelId") int labelId) {
		boolean result = noteService.removeLabelFromNote(noteId, labelId);
		if (result) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Label could'nt be removed from note", HttpStatus.CONFLICT);
		}
	}

	@PostMapping(value = "/addImage/{noteId}")
	public ResponseEntity<?> addImage(@RequestParam("file") MultipartFile file, @PathVariable("noteId") int noteId) throws IOException {
		if (noteService.addImages(file, noteId))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("Images couldn't be added", HttpStatus.CONFLICT);
	}
	
	@DeleteMapping(value = "/deleteImage/{imageId}")
	public ResponseEntity<?> deleteImage(@PathVariable("imageId") int imageId) throws IOException {
		if (noteService.deleteImage(imageId))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("Images couldn't be added", HttpStatus.CONFLICT);
	}

}
