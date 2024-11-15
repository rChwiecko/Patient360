import java.time.Duration;
import java.time.LocalDateTime;
public class FollowUp extends Appointment {

    public FollowUp(Patient patient, Doctor doctor, String description, LocalDateTime date, Hospital location, String preAppointmentInstructions, Duration appointmentDuration) {
        super(patient, doctor, "Follow Up Appointment", description, date, location, preAppointmentInstructions, appointmentDuration);
    }
}
