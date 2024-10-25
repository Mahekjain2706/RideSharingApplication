package com.ridesharing.web.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.ridesharing.web.models.ERole;
import com.ridesharing.web.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}