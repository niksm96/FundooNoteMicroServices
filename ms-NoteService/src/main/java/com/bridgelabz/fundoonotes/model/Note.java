package com.bridgelabz.fundoonotes.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Note")
public class Note {

	@Id
	@GeneratedValue
	@Column(name = "noteId")
	private int noteId;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "createdAt")
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(name = "updatedAt")
	@UpdateTimestamp
	private Timestamp updatedAt;

	@Column(name = "isArchive")
	private boolean isArchive;

	@Column(name = "isPinned")
	private boolean isPinned;

	@Column(name = "isTrashed")
	private boolean isTrashed;

	@Column(name = "userId")
	private int userId;
	
	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Label.class, cascade = { CascadeType.ALL })
	@JoinTable(name = "Note_Label", joinColumns = { @JoinColumn(name = "noteId") }, inverseJoinColumns = {
			@JoinColumn(name = "labelId") })
	private List<Label> listOfLabels;

	@Column(name = "color")
	private String color;
	
	@OneToMany(mappedBy = "noteId")
	private List<Collaborator> collaborators;
	
	@Column(name = "reminder")
	private Timestamp reminder;
	
	@Column(name = "fileNames")
	private StringBuilder fileNames;

	public int getNoteId() {
		return noteId;
	}

	public Note setNoteId(int noteId) {
		this.noteId = noteId;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Note setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Note setDescription(String description) {
		this.description = description;
		return this;
	}

	

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public Note setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public Note setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}

	public boolean isArchive() {
		return isArchive;
	}

	public Note setArchive(boolean isArchive) {
		this.isArchive = isArchive;
		return this;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public Note setPinned(boolean isPinned) {
		this.isPinned = isPinned;
		return this;
	}

	public boolean isTrashed() {
		return isTrashed;
	}

	public Note setTrashed(boolean isTrashed) {
		this.isTrashed = isTrashed;
		return this;
	}

	public int getUserId() {
		return userId;
	}

	public Note setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public List<Label> getListOfLabels() {
		return listOfLabels;
	}

	public Note setListOfLabels(List<Label> listOfLabels) {
		this.listOfLabels = listOfLabels;
		return this;
	}

	
	public String getColor() {
		return color;
	}

	public Note setColor(String color) {
		this.color = color;
		return this;
	}
	

	public List<Collaborator> getCollaborators() {
		return collaborators;
	}

	public void setCollaborators(List<Collaborator> collaborators) {
		this.collaborators = collaborators;
	}

	public Timestamp getReminder() {
		return reminder;
	}

	public Note setReminder(Timestamp reminder) {
		this.reminder = reminder;
		return this;
	}

	public StringBuilder getFileNames() {
		return fileNames;
	}

	public void setFileNames(StringBuilder fileNames) {
		this.fileNames = fileNames;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", title=" + title + ", description=" + description + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", isArchive=" + isArchive + ", isPinned=" + isPinned
				+ ", isTrashed=" + isTrashed + ", userId=" + userId + ", listOfLabels=" + listOfLabels + ", color="
				+ color + ", collaborators=" + collaborators + ", reminder=" + reminder + ", fileNames=" + fileNames
				+ "]";
	}

}
