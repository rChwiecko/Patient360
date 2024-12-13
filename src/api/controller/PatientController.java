package api.controller;
import api.models.*;
import java.util.List;
public class PatientController{
    private Hospital hospital;
    private List<Doctor> doctors;
    private List<Patient> patients;
    public PatientController(Hospital hospital)
    {
        this.hospital = hospital;
        this.doctors = hospital.getDoctors();
        this.patients = hospital.getPatients();
    } 

    
} 