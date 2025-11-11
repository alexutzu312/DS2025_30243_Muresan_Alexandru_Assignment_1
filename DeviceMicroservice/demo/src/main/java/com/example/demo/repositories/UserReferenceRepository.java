package com.example.demo.repositories;

import com.example.demo.entities.UserReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserReferenceRepository extends JpaRepository<UserReference, UUID> {
}
