package api.models;


import java.time.Duration;
import java.time.LocalDateTime;

public class Surgery extends Appointment {

    public Surgery(Patient patient, Doctor doctor, String description, LocalDateTime date, Hospital location, String preAppointmentInstructions, Duration appointmentDuration) {
        super(patient, doctor, "Surgery", description, date, location, preAppointmentInstructions, appointmentDuration);
    }
}
