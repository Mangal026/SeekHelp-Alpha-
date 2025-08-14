package com.seekhelp.desktop;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean authenticate(String username, String password) {
        // Simple authentication for demo purposes
        return ("admin".equals(username) && "admin123".equals(password)) ||
               ("guest".equals(username) && "guest123".equals(password));
    }

    public boolean isAdmin(String username) {
        return "admin".equals(username);
    }
}
