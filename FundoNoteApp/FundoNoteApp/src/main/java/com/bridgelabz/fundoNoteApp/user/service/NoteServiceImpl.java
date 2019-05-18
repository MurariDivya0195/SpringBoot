package com.bridgelabz.fundoNoteApp.user.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoNoteApp.user.model.Label;
import com.bridgelabz.fundoNoteApp.user.model.Note;
import com.bridgelabz.fundoNoteApp.user.repository.LabelRepository;
import com.bridgelabz.fundoNoteApp.user.repository.NoteRespository;
import com.bridgelabz.fundoNoteApp.util.TokenClass;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	


	@Autowired
	NoteRespository noteRepository;

	@Autowired
	private TokenClass tokenClass;

	@Autowired
	private LabelRepository labelRepository;

	@Override
	public Note createNote(Note note, String token) {
		int userId = tokenClass.parseJWT(token);
		System.out.println(userId);
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		note.setCreateOn(ts);
		note.setId(userId);
		return noteRepository.save(note);
	}

	@Override
	public Note findById(int userId) {
		List<Note> noteInfo = noteRepository.findById(userId);
		return noteInfo.get(0);
	}

	@Override
	public Note updateNote(Note note, String token) {
		int noteId = note.getNoteId();
		int userId = tokenClass.parseJWT(token);
		List<Note> noteInfo = noteRepository.findByNoteIdAndId(noteId, userId);
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		noteInfo.forEach(existingUser -> {
			existingUser
					.setCreateOn(note.getCreateOn() != null ? note.getCreateOn() : noteInfo.get(0).getCreateOn());
			existingUser.setDescription(
					note.getDescription() != null ? note.getDescription() : noteInfo.get(0).getDescription());
			existingUser.setTitle(note.getTitle() != null ? note.getTitle() : noteInfo.get(0).getTitle());
			/*
			 * existingUser .setUpdatedOn(note.getUpdatedOn() != null ? note.getUpdatedOn()
			 * : noteInfo.get(0).getUpdatedOn());
			 */
		});
		noteInfo.get(0).setUpdatedOn(ts);
		return noteRepository.save(noteInfo.get(0));

	}

	@Override
	public String deleteNote(int noteId, String token) {
		int userId = tokenClass.parseJWT(token);
		List<Note> noteInfo = noteRepository.findByNoteIdAndId(noteId, userId);
		noteRepository.delete(noteInfo.get(0));
		return "Deleted";
	}

	@Override
	public Note getNoteInfo(int noteId) {
		List<Note> noteInfo = noteRepository.findByNoteId(noteId);
		return noteInfo.get(0);
	}

	@Override
	public List<Note> getAllNotes() {

		return noteRepository.findAll();
	}

	@Override
	public List<Note> getNotes(String token) {
		int id = tokenClass.parseJWT(token);
		List<Note> list = noteRepository.findById(id);
		return list;
	}

	@Override
	public Label labelCreate(Label label, String token) {
		int userId = tokenClass.parseJWT(token);
		label.setId(userId);

		return labelRepository.save(label);
	}

	@Override
	public Label labelUpdate(Label label, String token,int labelId) {
		int userId = tokenClass.parseJWT(token);
		List<Label> list = labelRepository.findByIdAndLabelId(userId, labelId);
		list.forEach(userLabel -> {
			userLabel.setLabelName(label.getLabelName() != null ? label.getLabelName() : list.get(0).getLabelName());
		});
		label.setLabelId(labelId);
		label.setId(userId);
		return labelRepository.save(label);
	}

	@Override
	public String labelDelete(String token, int labelId) {
		int userId = tokenClass.parseJWT(token);
		List<Label> list = labelRepository.findByIdAndLabelId(userId, labelId);
		labelRepository.delete(list.get(0));
		return "Deleted";
	}

	@Override
	public List<Label> getLabels(String token) {
		int userId = tokenClass.parseJWT(token);
		List<Label> list = labelRepository.findById(userId);
		return list;
	}
}
		
	

