package com.bridgelabz.fundoonotes.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Collaborator", uniqueConstraints = @UniqueConstraint(columnNames = { "noteId", "userId" }))
public class Collaborator {

	@Id
	@GeneratedValue
	@Column(name = "collaboratorId")
	private int collaboratorId;

	@Column(name = "noteId")
	private int noteId;

	@Column(name = "userId")
	private int userId;

	@Column(name = "lastModified")
	@UpdateTimestamp
	private Timestamp lastModified;
	
	public int getNoteId() {
		return noteId;
	}

	public Collaborator setNoteId(int noteId) {
		this.noteId = noteId;
		return this;
	}

	public int getUserId() {
		return userId;
	}

	public Collaborator setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public Collaborator setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
		return this;
	}

	@Override
	public String toString() {
		return "Collaborator [collaboratorId=" + collaboratorId + ", noteId=" + noteId + ", userId=" + userId
				+ ", lastModified=" + lastModified + "]";
	}

}
