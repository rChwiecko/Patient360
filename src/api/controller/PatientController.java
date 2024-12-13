package api.controller;
import api.models.*;
import api.models.exceptions.PatientManagementException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
public class PatientController{
    private Hospital hospital;
    private List<Doctor> doctors;
    private List<Patient> patients;

    private Receptionist receptionist;
    public PatientController(Receptionist receptionist){
        this.hospital = receptionist.getLocation();
        this.doctors = hospital.getDoctors();
        this.patients = hospital.getPatients();
    }

    public List<Patient> getPatients(){
        return this.patients;
    }

    public List<Doctor> getDoctors(){
        return this.doctors;
    }

    public Hospital getHospital(){
        return this.hospital;
    }

    public Receptionist getReceptionist(){
        return this.receptionist;
    }

    public boolean addPatient(String firstName, String lastName, String email, String phoneNum, Doctor doctor){
        try {
            this.getReceptionist().addPatient(firstName, lastName, phoneNum, email, doctor);
            return true;
        } catch (PatientManagementException e) {
            return false;
        }
    }

    /**
     * leave additionalRecord null if you dont want to add anything to the Record, leave newPrescription null if no new prescription needs to be made
     * @param patient
     * @param additionalRecord
     * @param newPrescription
     */
    public void manageRecord(Patient patient, String additionalRecord, Prescription newPrescription){
        this.getReceptionist().managePatientRecord(patient, additionalRecord, newPrescription);
    }

    public Prescription createPrescription(Patient patient, String medicationName, String dosage, String frequency,
    String prescriptionDate, String expiryDate, String instructions, int refillCount){
        return new Prescription(prescriptionDate, patient, medicationName, dosage, frequency, prescriptionDate, expiryDate, instructions, refillCount);
    }

    /**
     * returns false is appointment was not successfully created, true if appointment was successfully created
     * @param patient
     * @param doctor
     * @param appointmentType
     * @param description
     * @param date
     * @param location
     * @param appointmentDuration
     * @param preAppointmentInstructions
     * @return
     */
    public boolean bookAppointment(Patient patient, Doctor doctor, String appointmentType, String description, LocalDateTime date, Hospital location, Duration appointmentDuration, String preAppointmentInstructions){
        return this.getReceptionist().makeAppointment(patient, doctor, appointmentType, description, date, location, appointmentDuration, preAppointmentInstructions);
    }
    
} 