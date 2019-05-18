package com.bridgelabz.fundoNoteApp.user.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Label {

	@Id
	private int labelId;
	private int id;
	private String LabelName;

	public int getLabelId() {
		return labelId;
	}

	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}

	

	public String getLabelName() {
		return LabelName;
	}

	public void setLabelName(String labelName) {
		LabelName = labelName;
	}

	@Override
	public String toString() {
		return "Label [LabelId=" + labelId + ", userId=" + id + ", LabelName=" + LabelName + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

}
