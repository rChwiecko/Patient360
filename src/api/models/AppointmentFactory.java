package api.models;

import java.time.Duration;
import java.time.LocalDateTime;

public class AppointmentFactory {

    public static Appointment createAppointment(String appointmentType, 
                                                Patient patient, 
                                                Doctor doctor, 
                                                String description, 
                                                LocalDateTime date, 
                                                Hospital location, 
                                                String preAppointmentInstructions, 
                                                Duration appointmentDuration) {

        return switch (appointmentType.toLowerCase()) {
            case "general" -> new GeneralConsultation(patient, doctor, description, date, location, preAppointmentInstructions, appointmentDuration);
            case "follow" -> new FollowUp(patient, doctor, description, date, location, preAppointmentInstructions, appointmentDuration);
            case "surgery" -> new Surgery(patient, doctor, description, date, location, preAppointmentInstructions, appointmentDuration);
            default -> throw new IllegalArgumentException("Unknown appointment type: " + appointmentType);
        };
    }
}
