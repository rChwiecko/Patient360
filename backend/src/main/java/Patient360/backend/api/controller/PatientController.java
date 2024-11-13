package Patient360.backend.api.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import Patient360.backend.service.PatientServices;
;

@RestController
public class PatientController {
    private PatientServices patientService;

    @Autowired
    public void PatientController(PatientServices patientService){
        this.patientService = patientService;
    }

    // @GetMapping
    // public Patient getPatient(@RequestBody Integer id){
    //     return null
    // }
}
