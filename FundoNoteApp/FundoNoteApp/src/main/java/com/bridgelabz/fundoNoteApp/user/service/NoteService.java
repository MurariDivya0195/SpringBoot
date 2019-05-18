package com.bridgelabz.fundoNoteApp.user.service;

import java.util.List;

import com.bridgelabz.fundoNoteApp.user.model.Label;
import com.bridgelabz.fundoNoteApp.user.model.Note;

public interface NoteService {
	public Note createNote(Note note, String token);

	public Note findById(int userId);

	public Note updateNote(Note note, String token);

	public String deleteNote(int noteId, String token);

	public Note getNoteInfo(int noteId);

	public List<Note> getAllNotes();

	public List<Note> getNotes(String token);

	public Label labelCreate(Label label, String token);

	public Label labelUpdate(Label label, String token,int labelId);

	public String labelDelete(String token, int labelId);

	public List<Label> getLabels(String token);

}
