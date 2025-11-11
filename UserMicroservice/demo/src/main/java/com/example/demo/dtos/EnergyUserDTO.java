package com.example.demo.dtos;

<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
=======
import java.util.Objects;
import java.util.UUID;

>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
public class EnergyUserDTO {
    private UUID id;
    private String username;
    private String password;
    private int age;
<<<<<<< HEAD
    private String role;

    public EnergyUserDTO(UUID id) {
        this.id = id;
    }

    public EnergyUserDTO(UUID id, String username, String password, String role) {
        this.id = id; this.username = username; this.password = password;
        this.role = role;
    }

    public EnergyUserDTO() {

    }
=======



    public EnergyUserDTO(UUID id, String username, String password) {
        this.id = id; this.username = username; this.password = password;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }


    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122


    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnergyUserDTO that = (EnergyUserDTO) o;
        return age == that.age && Objects.equals(username, that.username);
    }
    @Override public int hashCode() { return Objects.hash(username, age); }
}
