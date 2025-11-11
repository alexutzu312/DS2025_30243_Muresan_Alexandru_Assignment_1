package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
public class EnergyUserDTO {
    private UUID id;
    private String username;
    private String password;
    private int age;
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


    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnergyUserDTO that = (EnergyUserDTO) o;
        return age == that.age && Objects.equals(username, that.username);
    }
    @Override public int hashCode() { return Objects.hash(username, age); }
}
