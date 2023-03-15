package model;

import java.time.LocalDate;
import java.util.UUID;

public class Appointment {
  private UUID registrationId;
  private LocalDate date;

  /**
   * Constructor for Appointment
   * 
   * @param registrationId
   * @param date
   */
  public Appointment(UUID registrationId, LocalDate date) {
    this.registrationId = registrationId;
    this.date = date;
  }

  /**
   * @return the registrationId
   */
  public UUID getRegistrationId() {
    return registrationId;
  }

  /**
   * @return the date
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * @param registrationId the registrationId to set
   */
  public void setRegistrationId(UUID registrationId) {
    this.registrationId = registrationId;
  }

  /**
   * @param date the date to set
   */
  public void setDate(LocalDate date) {
    this.date = date;
  }

}
