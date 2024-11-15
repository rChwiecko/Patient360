import java.time.LocalDateTime;
import java.time.Duration;

public abstract class AppointmentFactory {
    public abstract Appointment createAppointment(Patient patient, Doctor doctor, String description, LocalDateTime date, Hospital location, String preAppointmentInstructions, Duration appointmentDuration);
}

// Factory class for GeneralConsultation
class GeneralConsultationFactory extends AppointmentFactory {
    @Override
    public Appointment createAppointment(Patient patient, Doctor doctor, String description, LocalDateTime date, Hospital location, String preAppointmentInstructions, Duration appointmentDuration) {
        return new GeneralConsultation(patient, doctor, description, date, location, preAppointmentInstructions, appointmentDuration);
    }
}

// Factory class for FollowUp
class FollowUpFactory extends AppointmentFactory {
    @Override
    public Appointment createAppointment(Patient patient, Doctor doctor, String description, LocalDateTime date, Hospital location, String preAppointmentInstructions, Duration appointmentDuration) {
        return new FollowUp(patient, doctor, description, date, location, preAppointmentInstructions, appointmentDuration);
    }
}

// Factory class for Surgery
class SurgeryFactory extends AppointmentFactory {
    @Override
    public Appointment createAppointment(Patient patient, Doctor doctor, String description, LocalDateTime date, Hospital location, String preAppointmentInstructions, Duration appointmentDuration) {
        return new Surgery(patient, doctor, description, date, location, preAppointmentInstructions, appointmentDuration);
    }
}
