package api.models;

import api.models.composite.*;
import api.models.exceptions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a Hospital entity that manages its members (Doctors, Patients, Receptionists)
 * and handles patient check-ins, check-outs, and hospital-related data.
 */
public class Hospital {
    private String address;                      // Address of the hospital
    private String department;                  // Department in the hospital
    private int capacity;                       // Current capacity of the hospital
    private List<Patient> patientsPresent;      // List of patients currently checked into the hospital
    private List<HospitalMember> members;       // List of all hospital members (Doctors, Patients, Receptionists)

    /**
     * Constructor to initialize the Hospital with an address, department, and capacity.
     * @param address    Hospital address
     * @param department Hospital department name
     * @param capacity   Current capacity of the hospital
     */
    public Hospital(String address, String department, int capacity) {
        this.address = address;
        this.department = department;
        this.capacity = capacity;
        this.patientsPresent = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    /**
     * Checks if the hospital has capacity for more patients.
     * @return true if capacity allows more patients, false otherwise
     */
    public boolean checkCapacity() {
        return this.patientsPresent.size() < getCapacity();
    }

    /**
     * Getter for the address of the hospital.
     * @return the hospital's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter for the department of the hospital.
     * @return the hospital's department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Retrieves the list of patients currently checked into the hospital.
     * @return List of patients currently present in the hospital
     */
    public List<Patient> getPatientsPresent() {
        return patientsPresent;
    }

    /**
     * Retrieves the list of all receptionists in the hospital.
     * @return List of Receptionist objects
     */
    public List<Receptionist> getReceptionists() {
        return members.stream()
                .filter(member -> member instanceof Receptionist)
                .map(member -> (Receptionist) member)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the list of all patients in the hospital system.
     * @return List of Patient objects
     */
    public List<Patient> getPatients() {
        return members.stream()
                .filter(member -> member instanceof Patient)
                .map(member -> (Patient) member)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the list of all doctors working in the hospital.
     * @return List of Doctor objects
     */
    public List<Doctor> getDoctors() {
        return members.stream()
                .filter(member -> member instanceof Doctor)
                .map(member -> (Doctor) member)
                .collect(Collectors.toList());
    }

    /**
     * Adds a doctor to the hospital system after verifying they don't already exist.
     * @param newDoctor The doctor to add
     * @throws DoctorManagementException if the doctor already exists
     */
    public void addDoctor(Doctor newDoctor) throws DoctorManagementException {
        for (Doctor doctor : this.getDoctors()) {
            if (doctor.getFirstName().equals(newDoctor.getFirstName()) && 
                doctor.getLastName().equals(newDoctor.getLastName())) {
                throw new DoctorManagementException("Doctor already exists");
            }
        }
        this.members.add(newDoctor);
    }

    /**
     * Checks a patient out of the hospital by removing them from the patientsPresent list.
     * @param newPatient The patient to check out
     * @throws PatientManagementException if the patient is not currently checked in
     */
    public void checkPatientOut(Patient newPatient) throws PatientManagementException {
        boolean found = false;
        for (Patient patient : this.getPatientsPresent()) {
            if (patient.getFirstName().equals(newPatient.getFirstName()) && 
                patient.getLastName().equals(newPatient.getLastName())) {
                found = true;
            }
        }
        if (!found) {
            throw new PatientManagementException("Patient was never checked in");
        } else {
            this.patientsPresent.remove(newPatient);
            setCapacity(getCapacity() - 1); // Decrease capacity
        }
    }

    /**
     * Checks a patient into the hospital by adding them to the patientsPresent list.
     * @param newPatient The patient to check in
     * @throws PatientManagementException if the patient is already checked in
     */
    public void checkPatientIn(Patient newPatient) throws PatientManagementException {
        for (Patient patient : this.getPatientsPresent()) {
            if (patient.getFirstName().equals(newPatient.getFirstName()) && 
                patient.getLastName().equals(newPatient.getLastName())) {
                throw new PatientManagementException("Patient already checked in");
            }
        }
        this.patientsPresent.add(newPatient);
        setCapacity(getCapacity() + 1); // Increase capacity
    }

    /**
     * Adds a patient to the hospital system (not necessarily checked in).
     * @param newPatient The patient to add
     * @throws PatientManagementException if the patient already exists in the system
     */
    public void addPatient(Patient newPatient) throws PatientManagementException {
        for (Patient patient : this.getPatients()) {
            if (patient.getFirstName().equals(newPatient.getFirstName()) && 
                patient.getLastName().equals(newPatient.getLastName())) {
                throw new PatientManagementException("Patient already exists");
            }
        }
        this.members.add(newPatient);
    }

    /**
     * Getter for the hospital's capacity.
     * @return current capacity of the hospital
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Setter for the hospital's capacity.
     * @param newCapacity The new capacity value to set
     */
    public void setCapacity(int newCapacity) {
        this.capacity = newCapacity;
    }
}
