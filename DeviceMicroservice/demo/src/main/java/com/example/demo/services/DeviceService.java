package com.example.demo.services;


import com.example.demo.dtos.DeviceDTO;
import com.example.demo.dtos.DeviceDetailsDTO;
<<<<<<< HEAD
import com.example.demo.dtos.UserReferenceDTO;
import com.example.demo.dtos.builders.DeviceBuilder;
import com.example.demo.entities.Device;
import com.example.demo.entities.UserReference;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.DeviceRepository;
import com.example.demo.repositories.UserReferenceRepository;
import jakarta.transaction.Transactional;
=======
import com.example.demo.dtos.builders.DeviceBuilder;
import com.example.demo.entities.Device;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.DeviceRepository;
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.PathVariable;
=======
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
<<<<<<< HEAD
    private final UserReferenceRepository userReferenceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, UserReferenceRepository userReferenceRepository) {
        this.deviceRepository = deviceRepository;
        this.userReferenceRepository = userReferenceRepository;
=======

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
    }

    public List<DeviceDTO> findDevice() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

<<<<<<< HEAD

    public List<DeviceDTO> findDevicesByUserId(UUID userId) {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .filter(device -> device.getUserReference().getId().equals(userId))
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public Device deleteDevice(UUID deviceId)
    {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
        if (deviceOptional.isEmpty()) {
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + deviceId);
        }

        deviceRepository.deleteById(deviceId);
        LOGGER.debug("Device with id {} was deleted in db", deviceId);
        return deviceOptional.get();
    }



    public Device updateDevice(UUID deviceId, DeviceDetailsDTO deviceDTO) {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", deviceId);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + deviceId);
        }

        Device device = deviceOptional.get();
        device.setName(deviceDTO.getName());
        device.setMaxConsumption(deviceDTO.getMaxConsumption());
        device.setUserReference(deviceDTO.getUserReference());

        device = deviceRepository.save(device);
        return device;
    }


    public DeviceDetailsDTO findDeviceById(UUID id) {
        Optional<Device> prosumerOptional = deviceRepository.findById(id);
        if (prosumerOptional.isEmpty()) {
=======
    public DeviceDetailsDTO findDeviceById(UUID id) {
        Optional<Device> prosumerOptional = deviceRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toDeviceDetailsDTO(prosumerOptional.get());
    }

<<<<<<< HEAD
    public UUID insert(DeviceDetailsDTO deviceDTO) {
        Device device = DeviceBuilder.toEntity(deviceDTO);
=======
    public UUID insert(DeviceDetailsDTO personDTO) {
        Device device = DeviceBuilder.toEntity(personDTO);
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was inserted in db", device.getId());
        return device.getId();
    }

<<<<<<< HEAD
    public void deleteUserReference(UUID userId){
        Optional<UserReference> personOptional = userReferenceRepository.findById(userId);
        if (personOptional.isEmpty()) {
            LOGGER.error("Energy User with id {} was not found in db", userId);
            throw new ResourceNotFoundException(UserReference.class.getSimpleName() + " with id: " + userId);
        }

        userReferenceRepository.deleteById(userId);
        LOGGER.debug("Energy User reference with id {} was deleted", userId);

    }
}
=======
}
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
