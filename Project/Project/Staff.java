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
	
		int choice = 0;
	
		// Validate input
		boolean validInput = false;
		while (!validInput) {
			System.out.print("Enter your choice: ");
	
			if (input.hasNextInt()) {
				choice = input.nextInt();
				validInput = true; // Valid input, exit loop
			} else {
				System.out.println("Invalid input. Please enter a number (1, 2, 3, or 4).");
				input.next(); // Consume the invalid input
			}
		}
	
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
		int visitorId;

		while (true) {
			System.out.print("Enter Visitor ID: ");
			if (input.hasNextInt()) {
				visitorId = input.nextInt();
				break; // Exit loop if valid integer
			} else {
				System.out.println("Please enter a valid integer for Visitor ID.");
				input.nextLine(); // Clear the invalid input
			}
		}

		// Search for the visitor using visitor's ID
		Visitor visitor = null;

		for (Visitor v : visitorList) {
			if (v.getVisitorId() == visitorId) {
				visitor = v;
				break;
			}
		}

		if (visitor == null) {
			System.out.println("Visitor ID " + visitorId + " is not registered.");
			return;
		}

		// Check if the patient name exists in the list of patients
		boolean patientExists = false; // Flag to check if patient exists
		String patientRoomNumber = ""; // Variable to store patient's room number

		for (Patient patient : patientList) { // Check against registered patients
			if (visitor.getPatientName().equalsIgnoreCase(patient.getName())) {
				patientExists = true; // Set flag if found
				patientRoomNumber = patient.getRoomNumber(); // Get room number
				break; // Exit loop if match found
			}
		}

		if (!patientExists) { // If no match found
			System.out.println("Patient name does not match any registered patient. Check-in denied.");
			return;
		}

		// Check access permission before proceeding with check-in/out
		if (!visitor.isAccessAllowed(patientRoomNumber)) {
			System.out.println(" Access denied for Visitor ID " + visitorId + " due to restricted area.");
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

				if (Integer.parseInt(patientRoomNumber) >= 100 && Integer.parseInt(patientRoomNumber) <= 110) {
					System.out.println("Access granted to Block A.");
				} else if (Integer.parseInt(patientRoomNumber) >= 111 && Integer.parseInt(patientRoomNumber) <= 120) {
					System.out.println("Access granted to Block B.");
				}
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
		int visitorId;

		while (true) {
			System.out.print("Enter Visitor ID to revoke access: ");
			if (input.hasNextInt()) {
				visitorId = input.nextInt();
				break; // Exit loop if valid integer
			} else {
				System.out.println("Please enter a valid integer for Visitor ID.");
				input.nextLine(); // Clear the invalid input
			}
		}

		boolean removed = visitorList.removeIf(visitor -> visitor.getVisitorId() == visitorId);

		if (removed) {
			System.out.println("Access revoked for Visitor ID " + visitorId);
		} else {
			System.out.println("No visitor found with ID " + visitorId);
		}
	}

	public void addNewVisitor(Scanner input) {
		int visitorId = getValidIntegerInput(input, "Enter Visitor ID: ");
		int accessId = getValidIntegerInput(input, "Enter Access ID: ");
	
		System.out.print("Enter Visitor Name: ");
		String visitorName = input.nextLine();
	
		String phoneNumber = "";
		while (true) {
			System.out.print("Enter Visitor Phone Number : ");
			phoneNumber = input.nextLine();
			if (isValidPhoneNumber(phoneNumber)) {
				break; // Exit loop if valid phone number
			} else {
				System.out.println("Invalid phone number. Please enter a number between 10 and 12 digits.");
			}
		}
	
		System.out.print("Enter Purpose of Visit: ");
		String purposeOfVisit = input.nextLine();
	
		System.out.print("Enter Patient Name: ");
		String patientName = input.nextLine();
	
		String patientRoomNumber = getPatientRoomNumber(patientName);
	
		if (patientRoomNumber == null) {
			System.out.println("Patient name does not match any registered patient. Visitor not added.");
			return;
		}
	
		Visitor newVisitor = new Visitor(visitorId, String.valueOf(accessId), visitorName, phoneNumber, purposeOfVisit,
				patientName, false);
	
		if (!newVisitor.isAccessAllowed(patientRoomNumber)) {
			System.out.println("Access denied for Visitor ID " + visitorId + " due to restricted area.");
			return;
		}
	
		// Add the new Visitor object to the list and log it.
		visitorList.add(newVisitor);
		VisitLog newVisitLog = new VisitLog(visitorId);
		visitLogManager.addVisitLog(newVisitLog);
	
		// Output which block the visitor can access
		grantAccessBasedOnRoomNumber(patientRoomNumber);
	}
	
	// Method to validate phone number
	private boolean isValidPhoneNumber(String phoneNumber) {
		return phoneNumber.matches("\\d{10,12}"); // Regex to check for 10 to 12 digits
	}
	
	
	private int getValidIntegerInput(Scanner input, String prompt) {
		int value = 0;
		while (true) {
			System.out.print(prompt);
			if (input.hasNextInt()) {
				value = input.nextInt();
				if (value > 0) { // Ensure positive integer
					input.nextLine(); // Consume the newline character
					break; // Exit loop if valid integer
				} else {
					System.out.println("Please enter a positive integer.");
					input.nextLine(); // Clear the invalid input
				}
			} else {
				System.out.println("Please enter a valid integer.");
				input.nextLine(); // Clear the invalid input
			}
		}
		return value;
	}
	
	private String getPatientRoomNumber(String patientName) {
		for (Patient patient : patientList) {
			if (patient.getName().equalsIgnoreCase(patientName)) {
				return patient.getRoomNumber();
			}
		}
		return null; // Return null if no matching patient is found
	}
	
	private void grantAccessBasedOnRoomNumber(String patientRoomNumber) {
		try {
			int roomNumber = Integer.parseInt(patientRoomNumber);
			
			if (roomNumber >= 100 && roomNumber <= 110) {
				System.out.println("New Visitor added successfully! Access granted to Block A.");
			} else if (roomNumber >= 111 && roomNumber <= 120) {
				System.out.println("New Visitor added successfully! Access granted to Block B.");
			} else if (roomNumber >= 121 && roomNumber <= 130) {
				System.out.println("New Visitor added successfully! Access granted to Block C.");
			} else {
				System.out.println("Invalid room number. Access Denied");
			}
			
		} catch (NumberFormatException e) {
			System.out.println("Error parsing room number. Access Denied");
		}
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
	
			// Validate input
			while (!input.hasNextInt()) {
				System.out.println("Invalid input. Please enter a number (0, 1, or 2).");
				input.next(); // Consume the invalid input
				System.out.print("Enter your choice: ");
			}
			
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
	