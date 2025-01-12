package Project;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private int id;
    private String name;
    private String roomNumber;

    public Patient(int id, String name, String roomNumber) {
        this.id = id;
        this.name = name;
        this.roomNumber = roomNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRoomNumber() {
        return roomNumber; // Getter for room number
    }

    public static List<Patient> createDummyPatients() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1, "Akmal Hakimi Bin Adnan", "101"));
        patients.add(new Patient(2, "Che Azfar Syakirin Bin Che Anuar", "114"));
        patients.add(new Patient(3, "Kee Jia Wen","127"));
        return patients;
    }
}
