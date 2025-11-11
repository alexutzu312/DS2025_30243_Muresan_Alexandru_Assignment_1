package com.example.demo.dtos;


import com.example.demo.dtos.validators.annotation.AgeLimit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
public class EnergyUserDetailsDTO {

    private UUID id;

    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
    private String role;



    public EnergyUserDetailsDTO(UUID id, String name, String address) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnergyUserDetailsDTO that = (EnergyUserDetailsDTO) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }


    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
