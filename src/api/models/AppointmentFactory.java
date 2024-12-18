package api.models;

import java.time.Duration;
import java.time.LocalDateTime;

public class AppointmentFactory {

    /**
     * Create appointments for factory
     * @param appointmentType
     * @param patient
     * @param doctor
     * @param description
     * @param date
     * @param location
     * @param preAppointmentInstructions
     * @param appointmentDuration
     * @return the new appointment object
     */
    public static Appointment createAppointment(String appointmentType, 
                                                Patient patient, 
                                                Doctor doctor, 
                                                String description, 
                                                LocalDateTime date, 
                                                String preAppointmentInstructions, 
                                                Duration appointmentDuration) {
        //switch statement that decides what kind of appointment to create
        return switch (appointmentType.toLowerCase()) {
            case "general" -> new GeneralConsultation(patient, doctor, description, date, preAppointmentInstructions, appointmentDuration);
            case "follow" -> new FollowUp(patient, doctor, description, date, preAppointmentInstructions, appointmentDuration);
            case "surgery" -> new Surgery(patient, doctor, description, date, preAppointmentInstructions, appointmentDuration);
            default -> throw new IllegalArgumentException("Unknown appointment type: " + appointmentType);
        };
    }
}
