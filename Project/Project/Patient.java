package Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public static Patient addPatient(Scanner scanner) {
        System.out.print("Enter Patient ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Patient Room Number: ");
        String roomNumber = scanner.nextLine();

        return new Patient(id, name, roomNumber);
    }

    public static List<Patient> createDummyPatients() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1, "Akmal Hakimi Bin Adnan", "101"));
        patients.add(new Patient(2, "Che Azfar Syakirin Bin Che Anuar", "102"));
        return patients;
    }
}
