package com.ridesharing.web.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document(collection = "trips")
public class Trip {
  @Id
  private String id;

  @NotBlank
  @Size(max = 20)
  private String driverName;

  @NotBlank
  @Size(max = 50)
  @Email
  private String driverPhone;

  @NotBlank
  @Size(max = 120)
  private String cabNumber;

  @NotBlank
  @Size(max = 20)
  @DocumentReference
  private ObjectId userId;

  @DocumentReference
  private List<ObjectId> companionUserIds;

  @DocumentReference
  private List<ObjectId> requestedCompanionUserIds;


  public Trip(String driverName, String driverPhone ,String cambNumber , ObjectId userId) {
    this.cabNumber = cabNumber;
    this.driverName = driverName;
    this.driverPhone = driverPhone;
    this.userId = userId;
  }

}