package com.kalbark0.edziennik.model;

import java.util.Objects;

public class Clazz {
	private String className;
	private int idSubject;
	private int idTeacher;
	public String getClassName() {
		return className;
	}
	public int getIdSubject() {
		return idSubject;
	}
	public int getIdTeacher() {
		return idTeacher;
	}
	
	public Clazz(String className, int idSubject, int idTeacher) {
		this.className = className;
		this.idSubject = idSubject;
		this.idTeacher = idTeacher;
	}
	@Override
	public int hashCode() {
		return Objects.hash(className, idSubject, idTeacher);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Clazz other = (Clazz) obj;
		return Objects.equals(className, other.className) && Objects.equals(idSubject, other.idSubject)
				&& Objects.equals(idTeacher, other.idTeacher);
	}
	
	
}
