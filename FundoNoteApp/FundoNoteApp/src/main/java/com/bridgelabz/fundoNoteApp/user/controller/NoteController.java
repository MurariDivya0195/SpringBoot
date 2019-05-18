package com.bridgelabz.fundoNoteApp.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoNoteApp.user.model.Note;
import com.bridgelabz.fundoNoteApp.user.service.NoteService;

@RestController
public class NoteController {

	@Autowired
	private NoteService noteService;
	
	@RequestMapping(value = "/notecreate", method = RequestMethod.POST)
	public Note noteCreate(@RequestBody Note note, HttpServletRequest request) {
		String token = request.getHeader("token");

		return noteService.createNote(note, token);

	}

	@RequestMapping(value = "/noteupdate", method = RequestMethod.PUT)
	public Note noteUpdate(@RequestBody Note note,HttpServletRequest request) {
		String token=request.getHeader("token");
		return noteService.updateNote(note, token);

	}

	@RequestMapping(value = "/notedelete/{noteId}", method = RequestMethod.DELETE)
	public String noteDelete(@PathVariable int noteId,HttpServletRequest request) {
		String token=request.getHeader("token");
		return noteService.deleteNote(noteId,token);

	}
	
	

	@RequestMapping(value = "/note/{noteId}", method = RequestMethod.GET)
	public Note noteInfo(@PathVariable int noteId) {
		return noteService.getNoteInfo(noteId);

	}

	@RequestMapping(value = "/notes", method = RequestMethod.GET)
	public List<Note> noteList() {
		return noteService.getAllNotes();
	}
	
	@RequestMapping(value="/notelist",method=RequestMethod.GET)
	public List<Note> noteList(HttpServletRequest request){
		String token=request.getHeader("token");
		return noteService.getNotes(token);
	}

}
