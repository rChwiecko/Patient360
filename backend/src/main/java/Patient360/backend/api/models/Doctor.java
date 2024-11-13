package Patient360.backend.api.models;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person {
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


    public void scheduleAppointment(Appointment newAppointment) {
        this.appointments.add(newAppointment);
    }

    public String getSpecialization() {
        return specialization;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public boolean getAvailability() {
        return availability;
    }

    public List<Appointment> getAppointments(){
        return this.appointments;
    }
}
