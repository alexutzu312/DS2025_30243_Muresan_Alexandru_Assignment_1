package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class DeviceDTO {
    private int maxConsumption;
    private UUID id;
    private String name;
    private UUID userId;


    /*
    public DeviceDTO(UUID id, String name, int age) {
        this.id = id; this.name = name; this.age = age;
    }
    */

    public DeviceDTO(UUID id, String name, int maxConsumption, UUID userId) {
        this.id = id; this.name = name; this.maxConsumption = maxConsumption;
        this.userId = userId;
    }



    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO that = (DeviceDTO) o;
        return Objects.equals(name, that.name);
    }
    @Override public int hashCode() { return Objects.hash(name); }
}
