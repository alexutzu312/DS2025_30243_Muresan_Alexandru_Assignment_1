package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;
=======
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;


import java.io.Serializable;
import java.util.UUID;

@Entity
<<<<<<< HEAD
@Getter
@Setter
public class EnergyUser implements Serializable {
=======
public class EnergyUser implements Serializable{
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

<<<<<<< HEAD
    @Column(nullable = false)
    private String role; // Ex: ROLE_ADMIN, ROLE_CLIENT


    public EnergyUser(String username, String password, String role) {
        this.username = username;
        this.role = role;
=======
    /*
    @Column(name = "age", nullable = false)
    private int age;
    */

    public EnergyUser(String username, String password) {
        this.username = username;
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
        this.password = password;
    }

    public EnergyUser() {

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
}
