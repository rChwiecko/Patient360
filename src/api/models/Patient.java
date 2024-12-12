package api.models;
import api.models.Observer.*;
import api.models.composite.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Patient extends Person implements HospitalMember{
    private String medicalRecordNum;
    private List<Prescription> currentMedications;
    private List<Appointment> appointments;
    private Doctor doctor;
    private String medicalRecord;
    private boolean checkIn;
    private List<Observer> observers = new ArrayList<>();
    // Constructor
    public Patient(String firstName, String lastName, String email, String phoneNum, String ID, String medicalRecordNum, Doctor doctor, String medicalRecord) {
        super(firstName, lastName, email, phoneNum, ID);
        this.medicalRecordNum = medicalRecordNum;
        this.currentMedications = new ArrayList<>();
        this.doctor = doctor;
        this.medicalRecord = medicalRecord;
        this.appointments = new ArrayList<>();
        this.checkIn = true; // assumes patient is at the hospital checked in when they are created
    }

    @Override
    public String getRole(){
        return "Patient";
    }

    /**
     * Books a new appointment for the patient.
     *
     * @param appointment the appointment to be booked
     */
    public void bookAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    /**
     * Adds a record to the patient's medical history.
     *
     * @param record the medical history record to add
     */
    public void addMedicalHistory(String record) {
        String str = "--" + record + "--";
        this.updateMedicalRecord(str);
    }

    /**
     * Gets the list of current medications.
     *
     * @return list of prescriptions
     */
    public List<Prescription> getCurrentMedications() {
        return currentMedications;
    }

    /**
     * Finds and returns the closest future appointment from today.
     *
     * @return the next appointment, or null if none exist
     */
    public Appointment getNextAppointment() {
        if (appointments == null || appointments.isEmpty()) {
            return null;
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

    /**
     * Gets the medical record of the patient.
     *
     * @return the patient's medical record
     */
    public String getMedicalRecord() {
        return medicalRecord;
    }

    /**
     * Gets the list of all appointments.
     *
     * @return list of appointments
     */
    public List<Appointment> getAppointments() {
        return this.appointments;
    }

    /**
     * Updates the patient's medical record with additional information.
     *
     * @param additional additional information to append to the medical record
     */
    public void updateMedicalRecord(String additional) {
        this.medicalRecord += additional;
    }

    /**
     * Adds a prescription to the current medications list.
     *
     * @param newPres the prescription to add
     */
    public void addPrescription(Prescription newPres) {
        this.currentMedications.add(newPres);
        notifyObservers("A new prescription has been added: " + newPres.getPrescriptionDetails());
    }

    /**
     * Marks the patient as checked in to the hospital.
     */
    public void checkIn() {
        this.checkIn = true;
    }

    /**
     * Marks the patient as checked out from the hospital.
     */
    public void checkOut() {
        this.checkIn = false;
    }

    /**
     * Updates the patient's assigned doctor.
     *
     * @param newDoctor the new doctor to assign
     */
    public void setDoctor(Doctor newDoctor) {
        this.doctor = newDoctor;
    }



        // Method to add an observer
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Method to notify all observers
    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    /**
     * returns patients check in status, ("are they checked into this hospital or not?")
     * @return checkIn
     */
    public boolean getCheckIn(){
        return this.checkIn;
    }

}
