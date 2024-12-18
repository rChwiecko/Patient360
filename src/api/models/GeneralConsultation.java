package api.models;


import java.time.Duration;
import java.time.LocalDateTime;

/**
 * class for General Consultation Appointment objects
 */
public class GeneralConsultation extends Appointment {

    /**
     * constructor for generalconsultation
     * @param patient
     * @param doctor
     * @param description
     * @param date
     * @param location
     * @param preAppointmentInstructions
     * @param appointmentDuration
     */
    public GeneralConsultation(Patient patient, Doctor doctor, String description, LocalDateTime date, String preAppointmentInstructions, Duration appointmentDuration) {
        super(patient, doctor, "General Consultation", description, date, Hospital.getInstance(), preAppointmentInstructions, appointmentDuration);
    }
}
