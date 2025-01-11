package Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Staff {
    private int staffId;
    private String password;
    private String name;
    private String role;

    private static List<Visitor> visitorList = new ArrayList<>();
    private static VisitLogManager visitLogManager = new VisitLogManager(); // Instance to manage visit logs
    private static List<Patient> patientList = Patient.createDummyPatients(); // List of registered patients

    // Constructor
    public Staff(int staffId, String password, String name, String role) {
        this.staffId = staffId;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    // Authenticate the staff
    public boolean authenticate(int staffId, String password) {
        return this.staffId == staffId && this.password.equals(password);
    }

    // Manage visitor check-in/check-out
    public void manageVisitorCheckInOut(Scanner input) {
        System.out.println("\n--- Visitor List ---");
        System.out.println("ID   Access ID   Status");

        // List all visitors with their current status
        if (visitorList.isEmpty()) {
            System.out.println("No visitors registered yet.");
        } else {
            for (Visitor visitor : visitorList) {
                String status = visitor.isCheckedIn() ? "Checked In" : "Checked Out";
                System.out.println(visitor.getVisitorId() + "  " + visitor.getAccessId() + "  " + status);
            }
        }

        System.out.println("\n--- Visitor Check-In/Check-Out ---");
        System.out.println("1. Check-In");
        System.out.println("2. Check-Out");
        System.out.println("3. Revoke Access");
        System.out.println("4. Add New Visitor");
        
		System.out.print("Enter your choice: ");
        
		int choice = input.nextInt();

		switch (choice) {
			case 1:
				checkInOrOutVisitor(input, true);
				break;
			case 2:
				checkInOrOutVisitor(input, false);
				break;
			case 3:
				revokeVisitorAccess(input);
				break;
			case 4:
				addNewVisitor(input);
				break;
			default:
				System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
		}
	}

	// Check-in or check-out a visitor
	public void checkInOrOutVisitor(Scanner input, boolean checkIn) {
		System.out.print("Enter Visitor ID: ");
		
		int visitorId = input.nextInt();

		// Search for the visitor using visitor's ID
		Visitor visitor = null;
		
		for (Visitor v : visitorList) {
			if (v.getVisitorId() == visitorId) {
				visitor = v;
				break;
			}
		}

		if (visitor == null) {
			System.out.println("Error: Visitor ID " + visitorId + " is not registered.");
			return;
		}

		// Check if the patient name exists in the list of patients
		boolean patientExists = false; // Flag to check if patient exists
		for (Patient patient : patientList) { // Check against registered patients
			if (visitor.getPatientName().equalsIgnoreCase(patient.getName())) {
				patientExists = true; // Set flag if found
				break; // Exit loop if match found
			}
		}

		if (!patientExists) { // If no match found
			System.out.println("Error: Patient name does not match any registered patient. Check-in denied.");
			return; 
		}

		VisitLog visitLog = visitLogManager.findLogById(visitorId); // Get visit log

		if (checkIn) {
			if (visitor.isCheckedIn()) {
				System.out.println("Visitor " + visitorId + " is already checked in.");
			} else {
				visitor.checkIn(); // Record check-in time
				visitLog.recordEntry(); // Log entry time
				System.out.println("Visitor " + visitorId + " is now checked in at " + visitLog.getEntryTime());
			}
		} else {
			if (!visitor.isCheckedIn()) {
				System.out.println("Visitor " + visitorId + " is already checked out.");
			} else {
				visitor.checkOut(); // Record check-out time
				visitLog.recordExit(); // Log exit time
				System.out.println("Visitor " + visitorId + " is now checked out at " + visitLog.getExitTime());
			}
		}
	}

	public void revokeVisitorAccess(Scanner input) {
	    System.out.print("Enter Visitor ID to revoke access: ");
	    
	    int visitorId = input.nextInt();

	    boolean removed = visitorList.removeIf(visitor -> visitor.getVisitorId() == visitorId);
	    
	    if (removed) {
	        System.out.println("Access revoked for Visitor ID " + visitorId);
	    } else {
	        System.out.println("No visitor found with ID " + visitorId);
	    }
	}

	public void addNewVisitor(Scanner input) {
	   System.out.print("Enter Visitor ID: ");
	   
	   int visitorId = input.nextInt();
	   
	   input.nextLine(); // Consume the newline character

	   System.out.print("Enter Access ID: ");
	   
	   String accessId = input.nextLine();

	   System.out.print("Enter Visitor Name: ");
	   
	   String visitorName = input.nextLine();

	   System.out.print("Enter Visitor Phone Number: ");
	   
	   String phoneNumber = input.nextLine();

	   System.out.print("Enter Purpose of Visit: ");
	   
	   String purposeOfVisit = input.nextLine();

       System.out.print("Enter Patient Name: "); 
       String patientName = input.nextLine();

       boolean patientExists = false; 
       for (Patient patient : patientList) { 
           if (patient.getName().equalsIgnoreCase(patientName)) { 
               patientExists = true; 
               break; 
           } 
       }

       if (!patientExists) { 
           System.out.println("Error: Patient name does not match any registered patient. Visitor not added.");
           return; 
       }

       Visitor newVisitor = new Visitor(visitorId, accessId, visitorName, phoneNumber, purposeOfVisit, patientName, false);

       // Add the new Visitor object to the list and log it.
       visitorList.add(newVisitor);
       VisitLog newVisitLog = new VisitLog(visitorId); 
       visitLogManager.addVisitLog(newVisitLog); 

       System.out.println("New Visitor added successfully!");
       System.out.println(newVisitor); 
   }

   public void displayMenu(Scanner input) {
       int choice;

       // Display staff's name and role.
       System.out.println("\nWelcome, " + name + " (" + role + ")");

       do {
           System.out.println("\n--- Staff Menu ---");
           System.out.println("1. Manage Visitor Check-In/Check-Out");
           System.out.println("2. Generate Visitor Report"); 
           System.out.println("0. Logout");
           System.out.print("Enter your choice: ");
           
           choice = input.nextInt();

           switch (choice) {
               case 1:
                   manageVisitorCheckInOut(input);
                   break;
               case 2:
                   Report.displayAllVisitors(visitorList, visitLogManager); 
                   break;
               case 0:
                   System.out.println("Logging out...");
                   break;
               default:
                   System.out.println("Invalid choice. Please try again.");
           }
       } while (choice != 0);
   }
}
