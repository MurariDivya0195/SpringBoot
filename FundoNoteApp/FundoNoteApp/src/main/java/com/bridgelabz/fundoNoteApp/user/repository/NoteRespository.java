package com.bridgelabz.fundoNoteApp.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoNoteApp.user.model.Label;
import com.bridgelabz.fundoNoteApp.user.model.Note;

@Repository
public interface NoteRespository extends JpaRepository<Note, Long> {

	List<Note> findById(int userId);

	List<Note> findByNoteIdAndId(int noteId, int userId);

	List<Note> findByNoteId(int noteId);

}
