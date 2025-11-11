<<<<<<< HEAD
package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String username;
    private String password;
    public String role;

=======
package com.example.demo.dtos;

public class LoginRequest {
    private String username;
    private String password;

    // Getters și Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
}