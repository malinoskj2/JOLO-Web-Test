package server.model.request;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
	private String email;
	private String password;
	public AuthenticationRequest() {}
	public AuthenticationRequest(String username, String password) {
    	this.email = username;
    	this.password = password;

    }

    public String getEmail() {
    	return email;

    }

    public String getPassword() {
    	return password;

    }

    public void setEmail(String email) {
    	this.email = email;

    }

    public void setPassword(String password) {
    	this.password = password;

    }

}

