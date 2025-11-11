package com.example.demo.dtos;


import com.example.demo.dtos.validators.annotation.AgeLimit;
import com.example.demo.entities.UserReference;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
public class DeviceDetailsDTO {

    private UUID id;
    private UserReference userReference;
    private String name;
    private int maxConsumption;


    public DeviceDetailsDTO() {
    }

    public DeviceDetailsDTO(String name, int maxConsumption) {
        this.name = name;
        this.maxConsumption = maxConsumption;
    }

    public DeviceDetailsDTO(UUID id, String name, int maxConsumption) {
        this.id = id;
        this.name = name;
        this.maxConsumption = maxConsumption;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDetailsDTO that = (DeviceDetailsDTO) o;
        return
                Objects.equals(name, that.name) &&
                Objects.equals(maxConsumption, that.maxConsumption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, maxConsumption);
    }
}
