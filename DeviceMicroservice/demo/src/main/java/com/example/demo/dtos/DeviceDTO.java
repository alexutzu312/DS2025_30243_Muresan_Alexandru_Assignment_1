package com.example.demo.dtos;

import java.util.Objects;
import java.util.UUID;

public class DeviceDTO {
    private int maxConsuption;
    private UUID id;
    private String name;
    private int age;

    /*
    public DeviceDTO(UUID id, String name, int age) {
        this.id = id; this.name = name; this.age = age;
    }
    */

    public DeviceDTO(UUID id, String name, int maxConsumption) {
        this.id = id; this.name = name; this.maxConsuption = maxConsumption;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO that = (DeviceDTO) o;
        return age == that.age && Objects.equals(name, that.name);
    }
    @Override public int hashCode() { return Objects.hash(name, age); }
}
