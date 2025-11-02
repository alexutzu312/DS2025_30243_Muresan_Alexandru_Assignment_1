package com.example.demo.repositories;

import com.example.demo.entities.EnergyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnergyUserRepository extends JpaRepository<EnergyUser, UUID> {

    /**
     * Example: JPA generate query by existing field
     */
    List<EnergyUser> findByUsername(String username);

    /**
     * Example: Custom query
     */

    @Query(value = "SELECT p " +
            "FROM EnergyUser p " +
            "WHERE p.username = :username " +
            "AND p.password = :password  ")
    Optional<EnergyUser> findSeniorsByName(@Param("name") String name);

}
