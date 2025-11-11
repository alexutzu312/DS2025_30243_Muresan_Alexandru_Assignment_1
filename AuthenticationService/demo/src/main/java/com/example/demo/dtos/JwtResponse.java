<<<<<<< HEAD
package com.example.demo.dtos;

import java.util.UUID;

public class JwtResponse {
    private String token;
    private UUID id;
    private String username;
    private String role; // Rolul utilizatorului

    public JwtResponse(String token, UUID id, String username, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.role = role;
    }

    // Getters și Setters
    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
=======
package com.example.demo.dtos;

public class JwtResponse {
    private String token;
    private Long id;
    private String username;
    private String role; // Rolul utilizatorului

    public JwtResponse(String token, Long id, String username, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.role = role;
    }

    // Getters și Setters
    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
}