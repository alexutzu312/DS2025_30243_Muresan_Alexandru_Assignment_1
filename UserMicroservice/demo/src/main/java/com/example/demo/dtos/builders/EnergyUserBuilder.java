package com.example.demo.dtos.builders;

import com.example.demo.dtos.EnergyUserDTO;
import com.example.demo.dtos.EnergyUserDetailsDTO;
import com.example.demo.entities.EnergyUser;

public class EnergyUserBuilder {

    private EnergyUserBuilder() {
    }

    public static EnergyUserDTO toEnergyUserDTO(EnergyUser energyUser) {
<<<<<<< HEAD
        return new EnergyUserDTO(energyUser.getId(), energyUser.getUsername(), energyUser.getPassword(), energyUser.getRole());
    }

    public static EnergyUserDetailsDTO toEnergyUserDetailsDTO(EnergyUser energyUser) {
        return new EnergyUserDetailsDTO(energyUser.getId(),energyUser.getUsername(), energyUser.getPassword());
=======
        return new EnergyUserDTO(energyUser.getId(), energyUser.getUsername(), energyUser.getPassword());
    }

    public static EnergyUserDetailsDTO toEnergyUserDetailsDTO(EnergyUser energyUser) {
        return new EnergyUserDetailsDTO(energyUser.getId(), energyUser.getUsername(), energyUser.getPassword());
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
    }

    public static EnergyUser toEntity(EnergyUserDetailsDTO energyUserDetailsDTO) {
        return new EnergyUser(energyUserDetailsDTO.getUsername(),
<<<<<<< HEAD
                energyUserDetailsDTO.getPassword(),
                energyUserDetailsDTO.getRole());
=======
                energyUserDetailsDTO.getPassword());
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
    }
}
