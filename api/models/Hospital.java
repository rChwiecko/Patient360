import java.util.ArrayList;
import java.util.List;

import Patient360.backend.api.models.exceptions.DoctorManagementException;


public class Hospital {
    private String address;
    private String department;
    private int capacity;
    private List<Patient> patientsPresent;
    private List<Doctor> doctors;
    private List<Receptionist> receptionists;
    private List<Patient> patients;

    // Constructor
    public Hospital(String address, String department, int capacity) {
        this.address = address;
        this.department = department;
        this.capacity = capacity;
        this.patientsPresent = new ArrayList<>();
        this.doctors = new ArrayList<>();
        this.receptionists = new ArrayList<>();
        this.patients = new ArrayList<>();
    }

    // Methods
    public boolean checkCapacity() {
        // Implementation
        return false;
    }

    public String getAddress() {
        return address;
    }

    public String getDepartment() {
        return department;
    }

    public List<Patient> getPatientsPresent() {
        return patientsPresent;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public List<Receptionist> getReceptionists() {
        return receptionists;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    /**
     * change patients doctor
     * @param patient
     * @param doctor
     * @return true if success, return false if any problems occur
     */
    public boolean changeDoctor(Patient patient, Doctor doctor){
        //prevent null pointer exception
        if (patient == null){
            return false;
        }
        try {
            patient.setDoctor(doctor);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public void addDoctor(Doctor newDoctor) throws DoctorManagementException{
        for (Doctor doctor: this.doctors){
            if (doctor.getFirstName().equals(newDoctor.getFirstName()) && doctor.getLastName().equals(newDoctor.getLastName())){
                throw new DoctorManagementException("Doctor already exists");
            }
        }
        this.doctors.add(newDoctor);
    }


    public void checkPatientOut(Patient newPatient) throws PatientManagementException{
        boolean found = false;
        for (Patient patient: this.patientsPresent){
            if (patient.getFirstName().equals(patient.getFirstName()) && patient.getLastName().equals(patient.getLastName())){
                found = true;
            }
        }
        if (!found){
            throw new PatientManagementException("Patient was never check in");
        }else{
            
        }

    }

    /**
     * method to check patient in, adds patient to list of patients CURRENTLY checked in to a specific hospital
     * @param newPatient
     * @throws PatientManagementException
     */
    public void checkPatientIn(Patient newPatient) throws PatientManagementException{
        for (Patient patient: this.getPatientsPresent()){
            if (patient.getFirstName().equals(newPatient.getFirstName()) && patient.getLastName().equals(newPatient.getLastName())){
                throw new PatientManagementException("Patient already checked in");
            }
        }
        this.getPatientsPresent().add(newPatient);
    }


    /**
     * method adds patient to a hospital list, this list is NOT the list of patients currently checked in, its the list of patients that are in the 
     * hospitals system
     * @param newPatient
     * @throws PatientManagementException if the patient is already in the system
     */
    public void addPatient(Patient newPatient) throws PatientManagementException{
        for (Patient patient: this.getPatients()){
            if (patient.getFirstName().equals(newPatient.getFirstName()) && patient.getLastName().equals(newPatient.getLastName())){
                throw new PatientManagementException("Patient already checked in");
            }
        }
        this.getPatients().add(newPatient);
    }
}

