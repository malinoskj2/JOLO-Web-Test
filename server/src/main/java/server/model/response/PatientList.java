package server.model.response;

import server.model.db.Patient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PatientList implements Serializable {

    private final List<String> patients;

    public PatientList(List<String> patientsIDs) {
        this.patients = new ArrayList<>();
        this.patients.addAll(patientsIDs);
    }


    public List<String> getPatients() {
        return patients;
    }
}
