package com.example.demo.services;

import com.example.demo.dtos.UserReferenceDTO;
import com.example.demo.entities.UserReference;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.UserReferenceRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserReferenceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserReferenceService.class);
    private final UserReferenceRepository userReferenceRepository;

    public UserReferenceService(UserReferenceRepository userReferenceRepository) {
        this.userReferenceRepository = userReferenceRepository;
    }

    @Transactional
    public UUID insert(UserReferenceDTO userReferenceDTO) {
        UserReference userReference = new UserReference(userReferenceDTO.getId());
        userReference = userReferenceRepository.save(userReference);
        LOGGER.debug("Device with id {} was inserted in db", userReference.getId());
        return userReference.getId();
    }

    @Transactional
    public void deleteUser(UUID userId) {
        Optional<UserReference> personOptional = userReferenceRepository.findById(userId);
        if (personOptional.isEmpty()) {
            LOGGER.error("Person with id {} was not found in db", userId);
            throw new ResourceNotFoundException(UserReference.class.getSimpleName() + " with id: " + userId);
        }
        userReferenceRepository.deleteById(userId);
        LOGGER.debug("Person reference with id {} was deleted", userId);
    }


}
