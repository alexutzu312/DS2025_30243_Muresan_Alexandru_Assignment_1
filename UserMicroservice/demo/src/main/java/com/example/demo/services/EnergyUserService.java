package com.example.demo.services;


import com.example.demo.dtos.EnergyUserDTO;
import com.example.demo.dtos.EnergyUserDetailsDTO;
import com.example.demo.dtos.builders.EnergyUserBuilder;
import com.example.demo.entities.EnergyUser;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.EnergyUserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EnergyUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyUserService.class);
    private final EnergyUserRepository energyUserRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    public EnergyUserService(EnergyUserRepository energyUserRepository) {
        this.energyUserRepository = energyUserRepository;
    }


    @Transactional
    public EnergyUser deleteEnergyUser(UUID energyUserId)
    {
        Optional<EnergyUser> personOptional = energyUserRepository.findById(energyUserId);
        if (personOptional.isEmpty()) {
            LOGGER.error("EnergyUser with id {} was not found in db", energyUserId);
            throw new ResourceNotFoundException(EnergyUser.class.getSimpleName() + " with id: " + energyUserId);
        }

        energyUserRepository.deleteById(energyUserId);
        LOGGER.debug("EnergyUser with id {} was deleted", energyUserId);

        String deviceServiceUrl = System.getenv("DEVICE_SERVICE_URL");
        if (deviceServiceUrl == null) {
            // Fallback to localhost if not running in Docker
            deviceServiceUrl = "http://localhost:8082";
        }
        String url = deviceServiceUrl + "/devices/delete-energy-user";
        url += "/" + energyUserId.toString();
        try {
            restTemplate.delete(url);
            LOGGER.debug("Deleted EnergyUser with id: {}", energyUserId);
        } catch (Exception e) {
            LOGGER.error("An error occurred while trying to delete EnergyUser with id: {}", energyUserId, e);
            throw e;
        }

        return personOptional.get();
    }

    public EnergyUser updateEnergyuser(UUID userId, EnergyUserDTO energyUserDTO) {
        Optional<EnergyUser> deviceOptional = energyUserRepository.findById(userId);
        if (deviceOptional.isEmpty()) {
            LOGGER.error("Device with id {} was not found in db", userId);
            throw new ResourceNotFoundException(EnergyUser.class.getSimpleName() + " with id: " + userId);
        }

        EnergyUser energyUser = deviceOptional.get();
        // se face update doar la username si role
        energyUser.setUsername(energyUserDTO.getUsername());
        energyUser.setRole(energyUserDTO.getRole());

        energyUser = energyUserRepository.save(energyUser);
        return energyUser;
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


    @Transactional
    public UUID insert(EnergyUserDetailsDTO UserDTO) {
        EnergyUser energyUser = EnergyUserBuilder.toEntity(UserDTO);
        energyUser = energyUserRepository.save(energyUser);
        LOGGER.debug("User with id {} was inserted in db", energyUser.getId());
        return energyUser.getId();
    }

}
