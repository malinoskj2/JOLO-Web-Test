package server.model;

import java.io.Serializable;

public class Authentication implements Serializable {
    private final String username;
    private final String password;

    public Authentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}

