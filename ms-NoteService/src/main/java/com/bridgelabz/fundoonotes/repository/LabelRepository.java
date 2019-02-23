package com.bridgelabz.fundoonotes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoonotes.model.Label;

public interface LabelRepository extends JpaRepository<Label, Integer> {

	Label findByLabelId(int labelId);

	List<Label> findAllByUserId(int userId);

	Optional<Label> findByUserIdAndLabelId(int userId, int labelId);
}
