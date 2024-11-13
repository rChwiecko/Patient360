package Patient360.backend.api.models;

import java.time.LocalDateTime;
import java.time.Duration;

public class FollowUp extends Appointment {

    public FollowUp(Patient patient, Doctor doctor, String description, LocalDateTime date, Hospital location, String preAppointmentInstructions, Duration appointmentDuration) {
        super(patient, doctor, "Follow Up Appointment", description, date, location, preAppointmentInstructions, appointmentDuration);
    }
}
