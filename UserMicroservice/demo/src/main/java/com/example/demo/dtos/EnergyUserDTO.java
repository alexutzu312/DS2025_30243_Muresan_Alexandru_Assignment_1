package com.example.demo.dtos;

import java.util.Objects;
import java.util.UUID;

public class EnergyUserDTO {
    private UUID id;
    private String username;
    private String password;
    private int age;



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


    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnergyUserDTO that = (EnergyUserDTO) o;
        return age == that.age && Objects.equals(username, that.username);
    }
    @Override public int hashCode() { return Objects.hash(username, age); }
}
