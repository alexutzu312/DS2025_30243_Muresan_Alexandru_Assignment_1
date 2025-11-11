package com.example.demo.controllers;

import com.example.demo.dtos.DeviceDTO;
import com.example.demo.dtos.DeviceDetailsDTO;
import com.example.demo.dtos.UserReferenceDTO;
import com.example.demo.entities.Device;
import com.example.demo.services.DeviceService;
import com.example.demo.services.UserReferenceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
@CrossOrigin
public class DeviceController {

    private final DeviceService deviceService;
    private final UserReferenceService userReferenceService;

    public DeviceController(DeviceService deviceService, UserReferenceService userReferenceService) {
        this.deviceService = deviceService;
        this.userReferenceService = userReferenceService;
    }

    // GET all devices (Admin view/Initial view)
    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        return ResponseEntity.ok(deviceService.findDevice());
    }

//    // NOU: GET devices by User ID (Client View)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeviceDTO>> getDevicesByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(deviceService.findDevicesByUserId(userId));
    }

    // NOU: PATCH Assign Device to User (Admin Only)
    @PatchMapping("/update/{deviceId}")
    public ResponseEntity<Void> updateDevice(@PathVariable UUID deviceId, @RequestBody @Valid DeviceDetailsDTO deviceDetailsDTO) {
        deviceService.updateDevice(deviceId, deviceDetailsDTO);
        return ResponseEntity.noContent().build(); // 204
    }

    // POST create device
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody DeviceDetailsDTO device) {
        UUID id = deviceService.insert(device);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build(); // 201
    }

    // GET device by ID
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDetailsDTO> getDevice(@PathVariable UUID id) {
        return ResponseEntity.ok(deviceService.findDeviceById(id));
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Device> deleteDevice(@PathVariable UUID deviceId) {
//        deviceService.deleteDevice(deviceId);
//        return ResponseEntity.noContent().build(); // 204
        return ResponseEntity.ok(deviceService.deleteDevice(deviceId));
    }

    @PostMapping("/add-user")
    public ResponseEntity<UUID> insertUserReference(@RequestBody UserReferenceDTO userReferenceDTO) {
        UUID userId = userReferenceService.insert(userReferenceDTO);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-energy-user/{userId}")
    public ResponseEntity<Void> deleteUserReference(@PathVariable UUID userId) {
        deviceService.deleteUserReference(userId);
        return ResponseEntity.noContent().build(); // 204
    }


}