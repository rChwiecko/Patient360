package api.models;


import api.models.composite.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class Doctor extends Person implements HospitalMember{
    private String specialization;
    private int yearsOfExperience;
    private List<Patient> patients;
    private List<Appointment> appointments;
    private boolean availability;

    // Constructor
    public Doctor(String firstName, String lastName, String email, String phoneNum, String ID, String specialization, int yearsOfExperience, boolean availability) {
        super(firstName, lastName, email, phoneNum, ID);
        this.specialization = specialization;
        this.yearsOfExperience = yearsOfExperience;
        this.availability = availability;
        this.patients = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }


    
    /** 
     * Adds new appointment to the list of appointments
     * @param newAppointment
     */
    public void scheduleAppointment(Appointment newAppointment) {
        this.appointments.add(newAppointment);
    }

    /**
     * returns Doctors specialization
     * @return specialization
     */
    public String getSpecialization() {
        return specialization;
    }

    @Override
    public String getRole(){
        return "Doctor";
    }

    /**
     * returns list of patients under that doctor
     * @return patients
     */
    public List<Patient> getPatients() {
        return patients;
    }

    /**
     * getter for list of doctors appointments
     * @return appointments
     */
    public List<Appointment> getAppointments(){
        return this.appointments;
    }
    
    /** 
     * method to check if a doctor is available at a specific time depending on their appointments
     * @param reqeustedTime
     */
    public boolean isAvailable(LocalDateTime requestedTime, Duration appointmentDuration) {
        for (Appointment appointment : this.appointments) {
            LocalDateTime start = appointment.getDate();
            LocalDateTime end = start.plus(appointment.getDuration()); // Use each appointment's actual duration
    
            // Check if the requested time overlaps with any existing appointment
            LocalDateTime requestedEnd = requestedTime.plus(appointmentDuration);
            if ((requestedTime.isBefore(end) && requestedTime.isAfter(start)) || // Start of new appointment overlaps
                (requestedEnd.isAfter(start) && requestedEnd.isBefore(end)) ||   // End of new appointment overlaps
                requestedTime.equals(start)) { // Exact start time match
                return false; // Doctor is not available
            }
        }
        return true; // Doctor is available
    }
}
