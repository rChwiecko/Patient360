package Patient360.backend.api.models;

import java.time.LocalDateTime;

public class GeneralConsultation extends Appointment {

    public GeneralConsultation(Patient patient, Doctor doctor, String description, LocalDateTime date, Hospital location, String preAppointmentInstructions) {
        super(patient, doctor, "General Consultation", description, date, location, preAppointmentInstructions);
    }
}
