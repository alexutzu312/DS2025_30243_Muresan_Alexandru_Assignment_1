package com.example.demo.dtos;

<<<<<<< HEAD
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

=======
import java.util.Objects;
import java.util.UUID;

public class DeviceDTO {
    private int maxConsuption;
    private UUID id;
    private String name;
    private int age;
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122

    /*
    public DeviceDTO(UUID id, String name, int age) {
        this.id = id; this.name = name; this.age = age;
    }
    */

<<<<<<< HEAD
    public DeviceDTO(UUID id, String name, int maxConsumption, UUID userId) {
        this.id = id; this.name = name; this.maxConsumption = maxConsumption;
        this.userId = userId;
    }


=======
    public DeviceDTO(UUID id, String name, int maxConsumption) {
        this.id = id; this.name = name; this.maxConsuption = maxConsumption;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO that = (DeviceDTO) o;
<<<<<<< HEAD
        return Objects.equals(name, that.name);
    }
    @Override public int hashCode() { return Objects.hash(name); }
=======
        return age == that.age && Objects.equals(name, that.name);
    }
    @Override public int hashCode() { return Objects.hash(name, age); }
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
}
