package com.example.demo.entities;

<<<<<<< HEAD
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
=======
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;


import java.io.Serializable;
import java.util.UUID;

<<<<<<< HEAD
@Getter
@Setter
=======
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
@Entity
public class Device implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "max_consumption", nullable = false)
    private int maxConsumption;

<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name = "user_reference", nullable = true)
    @JdbcTypeCode(java.sql.Types.BINARY)
    private UserReference userReference;

=======
    /*
    @Column(name = "age", nullable = false)
    private int age;
    */
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122

    public Device() {
    }

    public Device(String name, int maxConsumption) {
        this.name = name;
        this.maxConsumption = maxConsumption;
<<<<<<< HEAD
    }

    public Device(String name, int maxConsumption, UserReference userReference) {
        this.name = name;
        this.maxConsumption = maxConsumption;
        this.userReference = userReference;
    }

}
=======
        //this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(int maxConsumption) {
        this.maxConsumption = maxConsumption;
    }

    /*
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

     */
}
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
