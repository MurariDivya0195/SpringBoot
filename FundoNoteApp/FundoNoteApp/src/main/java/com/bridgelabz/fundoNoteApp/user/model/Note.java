package com.bridgelabz.fundoNoteApp.user.model;



import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Note {
	@Id
	private int noteId;
	private String title;
	private String description;
	private Timestamp createOn;
	private Timestamp updatedOn;
	private boolean isTrash;
	private boolean isArchieve;
	private boolean isPinned;
	private int id;

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Timestamp createOn) {
		this.createOn = createOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public boolean isTrash() {
		return isTrash;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

	public boolean isArchieve() {
		return isArchieve;
	}

	public void setArchieve(boolean isArchieve) {
		this.isArchieve = isArchieve;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", title=" + title + ", description=" + description + ", createOn=" + createOn
				+ ", updatedOn=" + updatedOn + ", isTrash=" + isTrash + ", isArchieve=" + isArchieve + ", isPinned="
				+ isPinned + ", id=" + id + "]";
	}
}
