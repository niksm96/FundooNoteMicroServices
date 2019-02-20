package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.service.NoteService;

@Controller
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@PostMapping(value = "/createnote")
	public ResponseEntity<?> createNote(@RequestBody Note note,@RequestHeader(value = "token") String token) {
			try {
				if (noteService.create(note, token))
					return new ResponseEntity<String>("Note created successfully",HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>("Note creation failed",HttpStatus.CONFLICT);
			}
		return new ResponseEntity<String>("Note creation failed",HttpStatus.CONFLICT);
	}
	
	@GetMapping(value = "/retrieve")
	public ResponseEntity<?> retrieve(@RequestHeader(value = "token") String token){
		List<Note> notes = noteService.retrieve(token);
		if(!notes.isEmpty())
			return new ResponseEntity<List<Note>>(notes, HttpStatus.FOUND);
		else
			return new ResponseEntity<String>("No notes to fetch", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value = "/updatenote")
	public ResponseEntity<?> updateNote(@RequestParam("noteId") int noteId,@RequestBody Note note,@RequestHeader(value = "token") String token){
		Note updatedNote = noteService.updateNote(noteId,note,token);
		if (updatedNote != null) {
			return new ResponseEntity<String>("Updated Successfully",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't update", HttpStatus.CONFLICT);
	}
	
	@DeleteMapping(value = "/deletenote")
	public ResponseEntity<?> delete(@RequestParam("noteId") int noteId,@RequestHeader(value = "token") String token ){
		boolean result = noteService.deleteNote(noteId,token);
		if (result) {
			return new ResponseEntity<String>("Deleted successfully",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't delete", HttpStatus.CONFLICT);
	}
	
	@PostMapping(value = "/createlabel")
	public ResponseEntity<?> createLabel(@RequestBody Label label, @RequestHeader(value = "token")	String token) {
			try {
				if (noteService.createLabel(label, token))
					return new ResponseEntity<String>("Label created successfully",HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>("Label creation unsuccessful",HttpStatus.CONFLICT);
			}
		return new ResponseEntity<String>("Label created unsuccessful",HttpStatus.CONFLICT);
	}
	
	@GetMapping(value = "/retrievelabel")
	public ResponseEntity<?> retrieveLabel(@RequestHeader(value = "token")	String token ){
		List<Label> labels = noteService.retrieveLabel(token);
		if(!labels.isEmpty())
			return new ResponseEntity<List<Label>>(labels, HttpStatus.FOUND);
		else
			return new ResponseEntity<String>("No labels to fetch", HttpStatus.NOT_FOUND);
	}
	
	@PutMapping(value = "/updatelabel")
	public ResponseEntity<?> updateLabel(@RequestParam("labelId") int labelId,@RequestBody Label label, @RequestHeader(value = "token") String token){
		Label updatedLabel = noteService.updateLabel(labelId,label,token);
		if (updatedLabel != null) {
			return new ResponseEntity<String>("Updated Successfully",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't update", HttpStatus.CONFLICT);
	}
	
	@DeleteMapping(value = "/deletelabel")
	public ResponseEntity<?> deleteLabel(@RequestParam("labelId") int labelId,@RequestHeader(value = "token") String token){
		boolean result = noteService.deleteLabel(labelId,token);
		if (result) {
			return new ResponseEntity<String>("Deleted successfully",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't delete", HttpStatus.CONFLICT);
	}
	
	@PutMapping(value = "/addlabeltonote")
	public ResponseEntity<?> addLabelToNote(@RequestParam("noteId") int noteId,@RequestParam("labelId") int labelId){
		boolean result = noteService.addLabelToNote(noteId, labelId);
		if(result) {
			return new ResponseEntity<String>("Label added to note successfully",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Label could'nt be added to note",HttpStatus.CONFLICT);
		}
	}
	
	@DeleteMapping(value = "/removelabelfromnote")
	public ResponseEntity<?> removeLabelFromNote(@RequestParam("noteId") int noteId,@RequestParam("labelId") int labelId){
		boolean result = noteService.removeLabelFromNote(noteId, labelId);
		if(result) {
			return new ResponseEntity<String>("Label removed successfully from note",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Label could'nt be removed from note",HttpStatus.CONFLICT);
		}
	}
}
