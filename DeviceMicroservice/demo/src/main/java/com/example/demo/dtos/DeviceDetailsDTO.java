package com.example.demo.dtos;


import com.example.demo.dtos.validators.annotation.AgeLimit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

public class DeviceDetailsDTO {

    private UUID id;

    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = " maximum consumption is required")
    private int maxConsumption;
    @NotNull(message = "age is required")
    @AgeLimit(value = 18)
    private Integer age;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDetailsDTO that = (DeviceDetailsDTO) o;
        return age == that.age &&
                Objects.equals(name, that.name) &&
                Objects.equals(maxConsumption, that.maxConsumption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, maxConsumption, age);
    }
}
