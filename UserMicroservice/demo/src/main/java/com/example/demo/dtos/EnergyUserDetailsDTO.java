package com.example.demo.dtos;


import com.example.demo.dtos.validators.annotation.AgeLimit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;
=======
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122

import java.util.Objects;
import java.util.UUID;

<<<<<<< HEAD

@Getter
@Setter
=======
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
public class EnergyUserDetailsDTO {

    private UUID id;

    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
<<<<<<< HEAD
    private String role;

=======
    @NotNull(message = "age is required")
    @AgeLimit(value = 18)
    private Integer age;
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122


    public EnergyUserDetailsDTO(UUID id, String name, String address) {
        this.id = id;
    }

<<<<<<< HEAD
=======
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    */
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnergyUserDetailsDTO that = (EnergyUserDetailsDTO) o;
<<<<<<< HEAD
        return Objects.equals(username, that.username) &&
=======
        return Objects.equals(age, that.age) &&
                Objects.equals(username, that.username) &&
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
                Objects.equals(password, that.password);
    }


    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
