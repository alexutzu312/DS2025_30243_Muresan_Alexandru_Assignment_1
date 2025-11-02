package com.example.demo.services;


import com.example.demo.dtos.EnergyUserDTO;
import com.example.demo.dtos.EnergyUserDetailsDTO;
import com.example.demo.dtos.builders.EnergyUserBuilder;
import com.example.demo.entities.EnergyUser;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.EnergyUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EnergyUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyUserService.class);
    private final EnergyUserRepository energyUserRepository;

    @Autowired
    public EnergyUserService(EnergyUserRepository energyUserRepository) {
        this.energyUserRepository = energyUserRepository;
    }

    public List<EnergyUserDTO> findEnergyUser() {
        List<EnergyUser> energyUserList = energyUserRepository.findAll();
        return energyUserList.stream()
                .map(EnergyUserBuilder::toEnergyUserDTO)
                .collect(Collectors.toList());
    }

    public EnergyUserDetailsDTO findEnergyUserById(UUID id) {
        Optional<EnergyUser> prosumerOptional = energyUserRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new ResourceNotFoundException(EnergyUser.class.getSimpleName() + " with id: " + id);
        }
        return EnergyUserBuilder.toEnergyUserDetailsDTO(prosumerOptional.get());
    }

    public UUID insert(EnergyUserDetailsDTO UserDTO) {
        EnergyUser energyUser = EnergyUserBuilder.toEntity(UserDTO);
        energyUser = energyUserRepository.save(energyUser);
        LOGGER.debug("User with id {} was inserted in db", energyUser.getId());
        return energyUser.getId();
    }

}
