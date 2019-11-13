package server.model.response;

import server.model.db.Patient;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class PatientList implements Serializable {

    private final List<Integer> patients;

    public PatientList(List<Patient> patients) {
            this.patients = patients.stream()
                    .mapToInt(patient -> patient.getPatientID())
                    .boxed()
                    .collect(Collectors.toList());
    }

    public List<Integer> getPatients() {
        return patients;
    }
}
