package Patient360.backend.api.models;

import java.time.LocalDateTime;

public class Surgery extends Appointment {

    public Surgery(Patient patient, Doctor doctor, String description, LocalDateTime date, Hospital location) {
        super(patient, doctor, "Follow Up Appointment", description, date, location);
    }
}
