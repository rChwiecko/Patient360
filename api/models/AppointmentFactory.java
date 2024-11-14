import java.time.LocalDateTime;
import java.time.Duration;
public abstract class AppointmentFactory {
    public abstract Appointment createAppointment(Patient patient, Doctor doctor, String description, LocalDateTime date, Hospital location, String preAppointmentInstructions, Duration appointmentDuration);
}
