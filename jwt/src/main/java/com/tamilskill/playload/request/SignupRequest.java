package com.tamilskill.playload.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {


@NotBlank
@Size(max = 50)
@Email (regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
	private String username;
	
private Set<String> role;

@NotBlank
@Size(min = 6,max = 40)
private String password;

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public Set<String> getRole() {
	return role;
}

public void setRole(Set<String> role) {
	this.role = role;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}


}
