package com.example.demo.controllers;

import com.example.demo.dtos.EnergyUserDTO;
import com.example.demo.dtos.JwtResponse;
import com.example.demo.dtos.LoginRequest;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

import com.example.demo.services.JwtUtilService;
import com.example.demo.services.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtilService jwtUtils;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getRole()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody LoginRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Eroare: Numele de utilizator este deja folosit!");
        }

        // Crează noul cont de utilizator (implicit ca și CLIENT, poți adăuga logica de roluri mai complexă)
        User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getRole());

        userRepository.save(user);

        String deviceMicroServiceUrl = System.getenv("DEVICE_MICROSERVICE_URL");
        if (deviceMicroServiceUrl == null) {
            deviceMicroServiceUrl = "http://localhost:8082";
        }
        String url = deviceMicroServiceUrl + "/devices/add-user";
        EnergyUserDTO energyUserDTO = new EnergyUserDTO(user.getId());

        try {
            restTemplate.postForObject(url, energyUserDTO, Void.class);
        } catch (Exception e) {
            throw e;
        }

//        return ResponseEntity.ok("Utilizatorul s-a înregistrat cu succes!");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/validate-token") // Traefik va apela această rută
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        // 1. Verifică headerul
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }

        String token = authHeader.substring(7);

        // 2. Validează tokenul STATELSS și extrage Claims (payload-ul)
        Claims claims = jwtUtils.validateJwtTokenAndGetClaims(token);

        if (claims != null) {
            // 3. Extrage Rolul (pentru RBAC-ul din Traefik)
            String role = jwtUtils.getRoleFromClaims(claims);

            // 4. Returnează 200 OK și headerele necesare pentru Traefik
            return ResponseEntity.ok()
                    .header("X-User-Role", role) // Rolul merge către Traefik
                    .build();
        }

        // Token invalid sau expirat
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
    }
}