package com.bridgelabz.fundoonotes.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "Image")
public class Image {
	
	@Id
	@GeneratedValue
	@Column(name = "imageId")
	private int imageId;
	
	@Lob
	@Column(name = "image")
	private byte[] image;
	
	@Column(name = "noteId")
	private int noteId;

	public int getImageId() {
		return imageId;
	}

	public Image setImageId(int imageId) {
		this.imageId = imageId;
		return this;
	}

	public byte[] getImage() {
		return image;
	}

	public Image setImage(byte[] image) {
		this.image = image;
		return this;
	}

	public int getNoteId() {
		return noteId;
	}

	public Image setNoteId(int noteId) {
		this.noteId = noteId;
		return this;
	}

	@Override
	public String toString() {
		return "Image [imageId=" + imageId + ", image=" + Arrays.toString(image) + ", noteId=" + noteId + "]";
	}
	
	
	
}
