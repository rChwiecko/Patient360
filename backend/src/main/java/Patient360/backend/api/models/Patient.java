package Patient360.backend.api.models;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class Patient extends Person {
    private String medicalRecordNum;
    private List<Prescription> currentMedications;
    private List<Appointment> appointments;
    private Doctor doctor;
    private String medicalRecord;
    private boolean checkIn;

    // Constructor
    public Patient(String firstName, String lastName, String email, String phoneNum, String ID, String medicalRecordNum, Doctor doctor, String medicalRecord) {
        super(firstName, lastName, email, phoneNum, ID);
        this.medicalRecordNum = medicalRecordNum;
        this.currentMedications = new ArrayList<>();
        this.doctor = doctor;
        this.medicalRecord = medicalRecord;
        this.appointments = new ArrayList<>();
        this.checkIn = true; //assumes patient is at the hospital checked in when they are created
    }

    // Methods
    public void bookAppointment(Appointment appointment) {
        this.getAppointments().add(appointment);
    }

    public void addMedicalHistory(String record) {
        String str = "--"+record+"--";
        this.updateMedicalRecord(str);
    }

    public List<Prescription> getCurrentMedications() {
        return currentMedications;
    }

    public Appointment getNextAppointment() {
        if (appointments == null || appointments.isEmpty()) {
            return null; // Return null if the list is empty
        }

        LocalDate today = LocalDate.now();
        Appointment closestAppointment = null;
        long closestDaysDifference = Long.MAX_VALUE;

        for (Appointment appointment : appointments) {
            long daysDifference = ChronoUnit.DAYS.between(today, appointment.getDate());

            // Check if this appointment is closer to today
            if (daysDifference >= 0 && daysDifference < closestDaysDifference) {
                closestDaysDifference = daysDifference;
                closestAppointment = appointment;
            }
        }

        return closestAppointment;
    }

    public String getMedicalRecord() {
        return medicalRecord;
    }

    public List<Appointment> getAppointments(){
        return this.appointments;
    }

    public void updateMedicalRecord(String additional){
        this.medicalRecord += additional;
    }

    public List<Prescription> getPrescriptions(){
        return this.currentMedications;
    }

    public void addPrescription(Prescription newPres){
        this.getCurrentMedications().add(newPres);
    }

    public void checkIn(){
        this.checkIn = true;
    }

    public void checkOut(){
        this.checkIn = false;
    }
}
