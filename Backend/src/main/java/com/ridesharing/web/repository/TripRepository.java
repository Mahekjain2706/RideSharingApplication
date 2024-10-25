package com.ridesharing.web.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ridesharing.web.models.Trip;

public interface TripRepository extends MongoRepository<Trip, String> {
  Boolean findByDriverName(String driverName);

}