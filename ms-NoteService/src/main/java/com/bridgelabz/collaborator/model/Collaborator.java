package com.bridgelabz.collaborator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Collaborator")
public class Collaborator {
	
	@Id
	@GeneratedValue
	@Column(name = "collaboratorId")
	private int collaboratorId;
	
	@Column(name = "noteId")
	private int noteId;
	
	@Column(name = "userId")
	private int userId;
	

}
