package api.models;


import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Class for Surgery objects
 */
public class Surgery extends Appointment {
    /**
     * Constructor for Surgery class
     * @param patient
     * @param doctor
     * @param description
     * @param date
     * @param location
     * @param preAppointmentInstructions
     * @param appointmentDuration
     */
    public Surgery(Patient patient, Doctor doctor, String description, LocalDateTime date, Hospital location, String preAppointmentInstructions, Duration appointmentDuration) {
        super(patient, doctor, "Surgery", description, date, location, preAppointmentInstructions, appointmentDuration);
    }
}
