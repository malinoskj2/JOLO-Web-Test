package server.model.request;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
    private final String email;
    private final String password;

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
}

