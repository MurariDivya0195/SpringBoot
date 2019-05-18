package com.bridgelabz.fundoNoteApp.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoNoteApp.user.model.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {


	List<Label> findById(int userId);

	List<Label> findByIdAndLabelId(int userId, int labelId);


}
