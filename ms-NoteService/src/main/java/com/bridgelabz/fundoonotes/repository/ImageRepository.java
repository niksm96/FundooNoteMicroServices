package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoonotes.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

}
