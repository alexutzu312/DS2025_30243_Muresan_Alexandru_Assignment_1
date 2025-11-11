package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class EnergyUserDTO {
    private UUID id;

    public EnergyUserDTO(UUID id) {
        this.id = id;
    }
}
