package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.util.UUID;

@Getter
@Setter
@Entity
public class UserReference {

    @Id
    @JdbcTypeCode(java.sql.Types.BINARY)
    private UUID id;

    public UserReference(UUID id) {
        this.id = id;
    }

    public UserReference() {

    }
}
