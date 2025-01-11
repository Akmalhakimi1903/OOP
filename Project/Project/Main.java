package Project;

import java.util.Scanner;

public class Main {
    private static Staff loggedInStaff = null;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        int choice;
        do {
            System.out.println("\n--- HVMS System ---");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    staffLogin(input);
                    break;
                case 2:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 2);

        input.close();
    }

    public static void staffLogin(Scanner input) {
        System.out.println("\n--- Staff Login ---");

        System.out.print("Enter Staff ID: ");
        int staffId = input.nextInt();
        input.nextLine(); // Consume the newline character

        System.out.print("Enter Password: ");
        String password = input.nextLine();

        Staff staff = new Staff(1, "1", "MOHAMAD SYAIFULLAH BIN MOKHTAR", "Admin");

        if (staff.authenticate(staffId, password)) {
            System.out.println("Login successful!");
            loggedInStaff = staff;
            loggedInStaff.displayMenu(input);
        } else {
            System.out.println("Invalid Staff ID or Password.");
        }
    }
}
