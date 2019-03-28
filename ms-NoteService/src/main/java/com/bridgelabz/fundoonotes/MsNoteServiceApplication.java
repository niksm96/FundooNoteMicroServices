package com.bridgelabz.fundoonotes;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bridgelabz.fundoonotes.service.NoteServiceImpl;

@SpringBootApplication
public class MsNoteServiceApplication {

	public static void main(String[] args) {
		new File(NoteServiceImpl.uploadDirectory).mkdirs();
		SpringApplication.run(MsNoteServiceApplication.class, args);
	}

}
