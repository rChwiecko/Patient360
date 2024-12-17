package api.models;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Abstract class representing a hospital appointment.
 * Contains details about the appointment such as the patient, doctor, date, and duration.
 */
public abstract class Appointment {
    private Patient patient;                   // The patient attending the appointment
    private Doctor doctor;                     // The doctor assigned to the appointment
    private String appointmentType;            // Type of appointment (e.g., general, follow-up, surgery)
    private String description;                // Additional description or notes about the appointment
    private LocalDateTime date;                // Date and time of the appointment
    private Hospital location;                 // Hospital where the appointment takes place
    private String status;                     // Status of the appointment (e.g., "incomplete", "complete")
    private String preAppointmentInstructions; // Instructions to the patient before the appointment
    private Duration duration;                 // Duration of the appointment

    /**
     * Constructor to initialize an Appointment object.
     *
     * @param patient                   The patient attending the appointment
     * @param doctor                    The doctor assigned to the appointment
     * @param appointmentType           The type of the appointment (e.g., general, follow-up, surgery)
     * @param description               Additional description or notes about the appointment
     * @param date                      The date and time of the appointment
     * @param location                  The hospital where the appointment will take place
     * @param preAppointmentInstructions Instructions provided to the patient before the appointment
     * @param appointmentDuration       The duration of the appointment
     */
    public Appointment(Patient patient, Doctor doctor, String appointmentType, String description,
                       LocalDateTime date, Hospital location, String preAppointmentInstructions,
                       Duration appointmentDuration) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentType = appointmentType;
        this.description = description;
        this.date = date;
        this.location = location;
        this.status = "incomplete"; // Default status when the appointment is created
        this.preAppointmentInstructions = preAppointmentInstructions;
        this.duration = appointmentDuration;
    }

    /**
     * Gets the patient assigned to this appointment.
     *
     * @return The patient object
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Gets the duration of the appointment.
     *
     * @return Duration object representing the appointment duration
     */
    public Duration getDuration() {
        return this.duration;
    }

    /**
     * Gets the doctor assigned to this appointment.
     *
     * @return The doctor object
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Gets the type of the appointment (e.g., "general", "follow-up", "surgery").
     *
     * @return A string representing the type of appointment
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * Gets the description of the appointment.
     *
     * @return A string containing additional notes or description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the date and time of the appointment.
     *
     * @return LocalDateTime representing the appointment date and time
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Updates the date of the appointment.
     *
     * @param newDate The new date and time to set for the appointment
     */
    public void setDate(LocalDateTime newDate) {
        this.date = newDate;
    }

    /**
     * Gets the hospital location where the appointment is scheduled.
     *
     * @return Hospital object
     */
    public Hospital getLocation() {
        return this.location;
    }

    /**
     * Gets the current status of the appointment (e.g., "incomplete", "complete").
     *
     * @return A string representing the appointment status
     */
    public String getAppointmentStatus() {
        return this.status;
    }

    /**
     * Updates the status of the appointment.
     *
     * @param newStatus The new status to set (e.g., "in progress", "complete")
     */
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    /**
     * Marks the appointment as complete by updating its status.
     */
    public void completeAppointment() {
        this.updateStatus("complete");
    }

    /**
     * Reschedules the appointment by changing its date and time.
     *
     * @param newDate The new date and time for the appointment
     */
    public void reschedule(LocalDateTime newDate) {
        this.setDate(newDate);
    }

    /**
     * Gets the instructions provided to the patient before the appointment.
     *
     * @return A string containing the pre-appointment instructions
     */
    public String getPreAppointmentInstructions() {
        return this.preAppointmentInstructions;
    }
}
