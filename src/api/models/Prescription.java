

public class Prescription {
    private String prescriptionId;
    private Patient patient;
    private String medicationName;
    private String dosage;
    private String frequency;
    private String duration;
    private String prescriptionDate;
    private String expiryDate;
    private String instructions;
    private int refillCount;

    // Constructor
    public Prescription(String prescriptionId, Patient patient, String medicationName, String dosage, String frequency,
                        String prescriptionDate, String expiryDate, String instructions, int refillCount) {
        this.prescriptionId = prescriptionId;
        this.patient = patient;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.prescriptionDate = prescriptionDate;
        this.expiryDate = expiryDate;
        this.instructions = instructions;
        this.refillCount = refillCount;
    }

    // Methods
    
    /** 
     * @return String
     */
    public String getPrescriptionId() {
        return prescriptionId;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getPrescriptionDate() {
        return prescriptionDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getRefillCount() {
        return refillCount;
    }

    public boolean isExpired() {
        // to be implemented
        return false;
    }

    public boolean canRefill() {
        // to be implemented
        return false;
    }

    public String getPrescriptionDetails() {
        // to be implemented
        return null;
    }
}
