package com.example.signup.login.payload.responce;

import java.util.List;

public class LoginResponce {
	
	private String jwt;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private List<String> roles;

	public LoginResponce(String jwt, String firstName, String lastName, String email, List<String> roles) {
		super();
		this.jwt = jwt;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roles = roles;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	

}
