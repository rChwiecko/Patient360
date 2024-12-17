package api.models;


import java.time.Duration;
import java.time.LocalDateTime;

/**
 * class for followup appointment objects
 */
public class FollowUp extends Appointment {
    /**
     * Constructor for the followup appointment
     * @param patient
     * @param doctor
     * @param description
     * @param date
     * @param location
     * @param preAppointmentInstructions
     * @param appointmentDuration
     */
    public FollowUp(Patient patient, Doctor doctor, String description, LocalDateTime date, Hospital location, String preAppointmentInstructions, Duration appointmentDuration) {
        super(patient, doctor, "Follow Up Appointment", description, date, location, preAppointmentInstructions, appointmentDuration);
    }
}
