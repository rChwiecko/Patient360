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
    /**
     * Constructor for controller class
     * @param receptionist
     */
    public PatientController(Receptionist receptionist){
        this.receptionist = receptionist;
        this.hospital = Hospital.getInstance();
        this.doctors = hospital.getDoctors();
        this.patients = hospital.getPatients();
    }

    /**
     * Getter for patients
     * @return List of Patients
     */
    public List<Patient> getPatients(){
        return this.hospital.getPatients();
    }

    /**
     * Getter for doctors
     * @return List of Doctors
     */
    public List<Doctor> getDoctors(){
        return this.hospital.getDoctors();
    }

    /**
     * Getter for hospital
     * @return Hospital
     */
    public Hospital getHospital(){
        return this.hospital;
    }

    /**
     * Getter for receptionist
     * @return Receptionist
     */
    public Receptionist getReceptionist(){
        return this.receptionist;
    }

    /**
     * Method to add patient to hospital
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNum
     * @param doctor
     * @return True if patient was added successfully, false otherwise
     */
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
    
    /**
     * @param patient
     * @param appointment
     * @return
     */
    public boolean checkInPatient(Patient patient, Appointment appointment){
        return this.getReceptionist().checkInPatient(patient, appointment);
    }


    /**
     * @param patient
     * @param appointment
     * @return
     */
    public boolean checkOutPatient(Patient patient, Appointment appointment){
        return this.getReceptionist().checkOutPatient(patient, appointment);
    }

} 
