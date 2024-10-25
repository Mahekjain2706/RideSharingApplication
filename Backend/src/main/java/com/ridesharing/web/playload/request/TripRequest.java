package com.ridesharing.web.playload.request;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TripRequest {
  @NotBlank
  private String driverName;

  @NotBlank
  private String driverPhone;

  @NotBlank
  private String cabNumber;

  @NotBlank
  private ObjectId userId;

  public String getDriverName() {
    return driverName;
  }

  public void setDriverName(String driverName) {
    this.driverName = driverName;
  }

  public String getDriverPhone() {
    return driverPhone;
  }

  public void setDriverPhone(String driverPhone) {
    this.driverPhone = driverPhone;
  }

  public String getcabNumber() {
    return cabNumber;
  }

  public void setCabNumber(String cabNumber) {
    this.cabNumber = cabNumber;
  }

  public ObjectId getUserId() {
    return userId;
  }

  public void setUserId(ObjectId userId) {
    this.userId = userId;
  }
}