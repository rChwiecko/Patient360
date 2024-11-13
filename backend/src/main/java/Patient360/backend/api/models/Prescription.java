package Patient360.backend.api.models;

class Prescription {
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
                        String duration, String prescriptionDate, String expiryDate, String instructions, int refillCount) {
        this.prescriptionId = prescriptionId;
        this.patient = patient;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
        this.prescriptionDate = prescriptionDate;
        this.expiryDate = expiryDate;
        this.instructions = instructions;
        this.refillCount = refillCount;
    }

    // Methods
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

    public String getDuration() {
        return duration;
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
        // Implementation
        return false;
    }

    public boolean canRefill() {
        // Implementation
        return false;
    }

    public String getPrescriptionDetails() {
        // Implementation
        return null;
    }
}
