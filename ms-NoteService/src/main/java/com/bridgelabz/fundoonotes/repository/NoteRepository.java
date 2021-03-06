package com.bridgelabz.fundoonotes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer>{
	Note findByNoteId(int noteId);
	
	Optional<Note> findByUserIdAndNoteId(int userId,int noteId);
	
	List<Note> findAllByUserId(int userId);
	
}
