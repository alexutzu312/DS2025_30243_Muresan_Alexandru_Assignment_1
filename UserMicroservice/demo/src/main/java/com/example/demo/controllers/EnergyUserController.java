package com.example.demo.controllers;

import com.example.demo.dtos.EnergyUserDTO;
import com.example.demo.dtos.EnergyUserDetailsDTO;
<<<<<<< HEAD
import com.example.demo.entities.EnergyUser;
=======
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
import com.example.demo.services.EnergyUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
<<<<<<< HEAD
@RequestMapping("/users")
@CrossOrigin
=======
@RequestMapping("/people")
@Validated
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
public class EnergyUserController {

    private final EnergyUserService energyUserService;

    public EnergyUserController(EnergyUserService energyUserService) {
        this.energyUserService = energyUserService;
    }

    @GetMapping
<<<<<<< HEAD
    public ResponseEntity<List<EnergyUserDTO>> getUsers() {
        return ResponseEntity.ok(energyUserService.findEnergyUser());
    }

    @PatchMapping("/update/{userId}")
    public ResponseEntity<Void> updateEnergyuser(@PathVariable UUID userId, @RequestBody EnergyUserDTO energyUserDTO) {
        energyUserService.updateEnergyuser(userId, energyUserDTO);
        return ResponseEntity.noContent().build(); // 204
    }

=======
    public ResponseEntity<List<EnergyUserDTO>> getPeople() {
        return ResponseEntity.ok(energyUserService.findEnergyUser());
    }

>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody EnergyUserDetailsDTO user) {
        UUID id = energyUserService.insert(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build(); // 201 + Location header
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnergyUserDetailsDTO> getEnergyUser(@PathVariable UUID id) {
        return ResponseEntity.ok(energyUserService.findEnergyUserById(id));
    }
<<<<<<< HEAD

    @DeleteMapping("/{id}")
    public ResponseEntity<EnergyUser> deleteEnergyUser(@PathVariable UUID id) {
        return ResponseEntity.ok(energyUserService.deleteEnergyUser(id));
    }
=======
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
}
