package Patient360.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import Patient360.backend.api.models.Patient;

@Service
public class PatientServices {
    private final Patient Patient;
    public PatientServices(){
        this.Patient = new Patient("Ryan", "Chwiecko", "RyanC10@g", 226448, 123);
    }
    
    public boolean getStatus(){
        return this.Patient.checkStatus();
    }

}