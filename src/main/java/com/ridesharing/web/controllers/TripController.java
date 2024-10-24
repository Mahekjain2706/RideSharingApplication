package com.ridesharing.web.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ridesharing.web.models.ERole;
import com.ridesharing.web.models.Role;
import com.ridesharing.web.models.Trip;
import com.ridesharing.web.models.User;
import com.ridesharing.web.playload.request.LoginRequest;
import com.ridesharing.web.playload.request.SignupRequest;
import com.ridesharing.web.playload.request.TripRequest;
import com.ridesharing.web.playload.response.MessageResponse;
import com.ridesharing.web.playload.response.UserInfoResponse;
import com.ridesharing.web.repository.RoleRepository;
import com.ridesharing.web.repository.TripRepository;
import com.ridesharing.web.repository.UserRepository;
import com.ridesharing.web.security.jwt.JwtUtils;
import com.ridesharing.web.security.services.UserDetailsImpl;

//for React Client (withCredentials)
// @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials="true")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/trip")
public class TripController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  TripRepository tripRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(new UserInfoResponse(userDetails.getId(),
                                   userDetails.getUsername(),
                                   userDetails.getEmail(),
                                   roles));
  }

  @PostMapping("/create")
  public ResponseEntity<?> registerUser(@Valid @RequestBody TripRequest tripRequest) {

    if (tripRepository.findByDriverName(tripRequest.getDriverName())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    Trip trip = new Trip(tripRequest.getDriverName(), 
                         tripRequest.getDriverPhone(),
                         tripRequest.getcabNumber(),
                         tripRequest.getUserId());

    tripRepository.save(trip);

    return ResponseEntity.ok(new MessageResponse("trip created successfully!"));
  }
}