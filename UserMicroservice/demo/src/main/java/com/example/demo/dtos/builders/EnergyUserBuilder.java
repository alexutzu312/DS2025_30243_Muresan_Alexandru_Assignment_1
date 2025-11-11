package com.example.demo.dtos.builders;

import com.example.demo.dtos.EnergyUserDTO;
import com.example.demo.dtos.EnergyUserDetailsDTO;
import com.example.demo.entities.EnergyUser;

public class EnergyUserBuilder {

    private EnergyUserBuilder() {
    }

    public static EnergyUserDTO toEnergyUserDTO(EnergyUser energyUser) {
        return new EnergyUserDTO(energyUser.getId(), energyUser.getUsername(), energyUser.getPassword(), energyUser.getRole());
    }

    public static EnergyUserDetailsDTO toEnergyUserDetailsDTO(EnergyUser energyUser) {
        return new EnergyUserDetailsDTO(energyUser.getId(),energyUser.getUsername(), energyUser.getPassword());
    }

    public static EnergyUser toEntity(EnergyUserDetailsDTO energyUserDetailsDTO) {
        return new EnergyUser(energyUserDetailsDTO.getUsername(),
                energyUserDetailsDTO.getPassword(),
                energyUserDetailsDTO.getRole());
    }
}
