package Patient360.backend.api.models;

import java.time.LocalDateTime;

public class FollowUp extends Appointment {

    public FollowUp(Patient patient, Doctor doctor, String description, LocalDateTime date, Hospital location, String preAppointmentInstructions) {
        super(patient, doctor, "Follow Up Appointment", description, date, location, preAppointmentInstructions);
    }
}
