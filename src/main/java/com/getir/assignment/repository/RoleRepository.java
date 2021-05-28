package com.getir.assignment.repository;

import com.getir.assignment.domain.Role;
import com.getir.assignment.security.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(Roles name);
}