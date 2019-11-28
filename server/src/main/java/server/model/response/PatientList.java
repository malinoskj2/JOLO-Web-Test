package server.model.response;

import server.model.db.Patient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PatientList implements Serializable {

    private final List<String> patients;

    public PatientList(List<Patient> patients) {
        this.patients = new ArrayList<>();
        for(Patient p : patients)
            this.patients.add(p.getPatientID());
        /*
            this.patients = patients.stream()
                    .mapToInt(patient -> patient.getPatientID())
                    .boxed()
                    .collect(Collectors.toList());
        //*/
    }


    public List<String> getPatients() {
        return patients;
    }
}
