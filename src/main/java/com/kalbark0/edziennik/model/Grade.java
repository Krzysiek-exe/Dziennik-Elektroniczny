package com.kalbark0.edziennik.model;

public class Grade {
	private int grade;
	private String description;
	public int getGrade() {
		return grade;
	}
	public String getDescription() {
		return description;
	}
	public Grade(int grade, String description) {
		this.grade = grade;
		this.description = description;
	}
}
