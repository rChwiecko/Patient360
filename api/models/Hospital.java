import composite.HospitalMember;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class Hospital {
    private String address;
    private String department;
    private int capacity;
    private List<Patient> patientsPresent;
    private List<HospitalMember> members;
    // Constructor
    public Hospital(String address, String department, int capacity) {
        this.address = address;
        this.department = department;
        this.capacity = capacity;
        this.patientsPresent = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    // Methods
    
    /** 
     * @return boolean
     */
    public boolean checkCapacity() {
        return this.patientsPresent.size() < getCapacity();
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

    public List<Receptionist> getReceptionists() {
        return members.stream()
                .filter(member -> member instanceof Receptionist)
                .map(member -> (Receptionist) member)
                .collect(Collectors.toList());
    }
    
    public List<Patient> getPatients() {
        return members.stream()
                .filter(member -> member instanceof Patient)
                .map(member -> (Patient) member)
                .collect(Collectors.toList());
    }


    public List<Doctor> getDoctors() {
        return members.stream()
                .filter(member -> member instanceof Doctor)
                .map(member -> (Doctor) member)
                .collect(Collectors.toList());
    }

    public void addDoctor(Doctor newDoctor) throws DoctorManagementException{
        for (Doctor doctor: this.getDoctors()){
            if (doctor.getFirstName().equals(newDoctor.getFirstName()) && doctor.getLastName().equals(newDoctor.getLastName())){
                throw new DoctorManagementException("Doctor already exists");
            }
        }
        this.members.add(newDoctor);
    }

    /**
     * removes patient from the patientsPresent list
     * @param newPatient
     * @throws PatientManagementException
     */
    public void checkPatientOut(Patient newPatient) throws PatientManagementException{
        boolean found = false;
        for (Patient patient: this.getPatientsPresent()){
            if (patient.getFirstName().equals(patient.getFirstName()) && patient.getLastName().equals(patient.getLastName())){
                found = true;
            }
        }
        if (!found){
            throw new PatientManagementException("Patient was never check in");
        }else{
            this.patientsPresent.remove(newPatient);
            setCapacity(getCapacity()-1);
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
        this.patientsPresent.add(newPatient);
        setCapacity(getCapacity() + 1);
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
        this.members.add(newPatient);
    }

    /**
     * getter for capacity
     * @return capacity
     */
    public int getCapacity(){
        return this.capacity;
    }

    /**
     * setter for capacity
     * @param newCapacity
     */
    public void setCapacity(int newCapacity){
        this.capacity =  newCapacity;
    }
}

