package com.kalbark0.edziennik.model;

public class NewUserQuery {
	private String firstName;
	private String lastName;
	private String zawod_user;
	private String nick_user;
	private String password;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getZawod_user() {
		return zawod_user;
	}
	public void setZawod_user(String zawod_user) {
		this.zawod_user = zawod_user;
	}
	public String getNick_user() {
		return nick_user;
	}
	public void setNick_user(String nick_user) {
		this.nick_user = nick_user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
