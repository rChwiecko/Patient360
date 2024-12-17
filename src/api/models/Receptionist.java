package api.models;

import api.models.Observer.NotificationService;
import api.models.composite.*;
import api.models.exceptions.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Receptionist working at a hospital.
 * A receptionist can manage appointments, patients, and their records.
 */
public class Receptionist extends Person implements HospitalMember {
    private String workShift;                          // Work shift of the receptionist (e.g., "day" or "night")
    private List<Appointment> handledAppointments;    // List of appointments handled by this receptionist
    private Hospital recepHospital;                   // Hospital where the receptionist works

    /**
     * Constructor to initialize a Receptionist object.
     *
     * @param firstName  The first name of the receptionist
     * @param lastName   The last name of the receptionist
     * @param email      The email address of the receptionist
     * @param phoneNum   The phone number of the receptionist
     * @param ID         The unique ID for the receptionist
     * @param workShift  The shift the receptionist works (e.g., "day", "night")
     * @param hospital   The hospital where the receptionist works
     */
    public Receptionist(String firstName, String lastName, String email, String phoneNum, String ID,
                        String workShift, Hospital hospital) {
        super(firstName, lastName, email, phoneNum, ID);
        this.workShift = workShift;
        this.recepHospital = hospital;
        this.handledAppointments = new ArrayList<>();
    }

    /**
     * Gets the role of the receptionist.
     *
     * @return "Receptionist"
     */
    @Override
    public String getRole() {
        return "Receptionist";
    }

    /**
     * Changes the doctor assigned to a patient.
     *
     * @param patient The patient whose doctor is to be changed
     * @param doctor  The new doctor to assign to the patient
     * @return true if successful, false otherwise
     */
    public boolean changeDoctor(Patient patient, Doctor doctor) {
        if (patient == null) {
            return false;
        }
        try {
            patient.setDoctor(doctor);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Cancels an appointment and removes it from both doctor and patient records.
     *
     * @param appointment The appointment to be cancelled
     * @throws AppointmentException If the appointment is null or cannot be removed
     */
    public void cancelAppointment(Appointment appointment) throws AppointmentException {
        if (appointment == null) {
            throw new AppointmentException("Empty appointment");
        }

        handledAppointments.remove(appointment);

        // Remove appointment from doctor's list
        if (appointment.getDoctor() != null) {
            appointment.getDoctor().getAppointments().remove(appointment);
        }

        // Remove appointment from patient's list
        if (appointment.getPatient() != null) {
            appointment.getPatient().getAppointments().remove(appointment);
        }
    }

    /**
     * Updates a patient's medical record with additional information or a prescription.
     *
     * @param patient           The patient whose record is updated
     * @param additionalRecord  Additional medical information to add
     * @param newPrescription   A new prescription to add
     */
    public void managePatientRecord(Patient patient, String additionalRecord, Prescription newPrescription) {
        if (additionalRecord != null) {
            patient.updateMedicalRecord(additionalRecord);
        }
        if (newPrescription != null) {
            patient.addPrescription(newPrescription);
        }
    }

    /**
     * Checks in a patient and marks an optional appointment as "in progress".
     *
     * @param patient     The patient to check in
     * @param appointment The appointment associated with the patient (optional)
     * @return true if successful, false otherwise
     */
    public boolean checkInPatient(Patient patient, Appointment appointment) {
        if (patient != null) {
            patient.checkIn();
            if (this.getLocation() != null) {
                try {
                    this.getLocation().checkPatientIn(patient);
                } catch (PatientManagementException e) {
                    return false;
                }
            }
            if (appointment != null) {
                appointment.updateStatus("in progress");
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks out a patient and marks an optional appointment as "complete".
     *
     * @param patient     The patient to check out
     * @param appointment The appointment associated with the patient (optional)
     * @return true if successful, false otherwise
     */
    public boolean checkOutPatient(Patient patient, Appointment appointment) {
        if (patient != null) {
            patient.checkOut();
            if (this.getLocation() != null) {
                try {
                    this.getLocation().checkPatientOut(patient);
                } catch (PatientManagementException e) {
                    return false;
                }
            }
            if (appointment != null) {
                appointment.updateStatus("complete");
            }
            return true;
        }
        return false;
    }

    /**
     * Gets the hospital where the receptionist works.
     *
     * @return Hospital object
     */
    public Hospital getLocation() {
        return this.recepHospital;
    }

    /**
     * Changes the hospital location for the receptionist.
     *
     * @param newHospital The new hospital to assign
     */
    public void changeLocation(Hospital newHospital) {
        this.recepHospital = newHospital;
    }

    /**
     * Creates and schedules an appointment for a patient with a doctor.
     * Notifies the patient of the new appointment using the observer pattern.
     *
     * @param patient                  The patient for the appointment
     * @param doctor                   The doctor to assign for the appointment
     * @param appointmentType          Type of the appointment (e.g., "general", "follow", "surgery")
     * @param description              Description of the appointment
     * @param date                     Date and time of the appointment
     * @param location                 Hospital location
     * @param appointmentDuration      Duration of the appointment
     * @param preAppointmentInstructions Instructions for the patient before the appointment
     * @return true if appointment is successfully created, false otherwise
     */
    public boolean makeAppointment(Patient patient, Doctor doctor, String appointmentType, String description,
                                   LocalDateTime date, Hospital location, Duration appointmentDuration,
                                   String preAppointmentInstructions) {
        if (doctor.isAvailable(date, appointmentDuration)) {
            Appointment appointment = AppointmentFactory.createAppointment(appointmentType, patient, doctor,
                    description, date, location, preAppointmentInstructions, appointmentDuration);

            // Schedule the appointment and notify the patient
            doctor.scheduleAppointment(appointment);
            patient.addObserver(new NotificationService());
            patient.bookAppointment(appointment);

            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a new patient to the hospital system and checks them in.
     *
     * @param firstName    First name of the patient
     * @param lastName     Last name of the patient
     * @param phoneNumber  Phone number of the patient
     * @param email        Email address of the patient
     * @param doctor       Doctor assigned to the patient
     * @throws PatientManagementException If the patient already exists in the hospital
     */
    public void addPatient(String firstName, String lastName, String phoneNumber, String email, Doctor doctor)
            throws PatientManagementException {
        Patient newPatient = new Patient(firstName, lastName, email, phoneNumber, "12", "3", doctor, null);

        for (Patient patient : this.getLocation().getPatients()) {
            if (patient.getFirstName().equals(newPatient.getFirstName()) &&
                patient.getLastName().equals(newPatient.getLastName())) {
                throw new PatientManagementException("Patient already exists");
            }
        }

        this.getLocation().addPatient(newPatient);
        this.checkInPatient(newPatient, null);
    }
}
