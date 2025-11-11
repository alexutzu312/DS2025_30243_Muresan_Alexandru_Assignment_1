package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;


import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "user_reference", nullable = true)
    @JdbcTypeCode(java.sql.Types.BINARY)
    private UserReference userReference;


    public Device() {
    }

    public Device(String name, int maxConsumption) {
        this.name = name;
        this.maxConsumption = maxConsumption;
    }

    public Device(String name, int maxConsumption, UserReference userReference) {
        this.name = name;
        this.maxConsumption = maxConsumption;
        this.userReference = userReference;
    }

}