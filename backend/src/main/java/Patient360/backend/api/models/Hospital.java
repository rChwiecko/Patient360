package Patient360.backend.api.models;
import java.util.ArrayList;
import java.util.List;

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

    public void addDoctor(Doctor newDoctor){
        this.doctors.add(newDoctor);
    }


    public void checkPatientOut(Patient patient){
        //implement
    }

    public void checkPatientIn(Patient patient){
        this.patientsPresent.add(patient);
    }

    public void addPatient(Patient patient){
        //add patient to overall list, do appropriate checking to make sure that the patient is actually in the hospital list
    }
}

