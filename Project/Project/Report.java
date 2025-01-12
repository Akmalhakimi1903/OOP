package Project;

import java.util.List;

public class Report {

    public static void displayAllVisitors(List<Visitor> visitorList, VisitLogManager visitLogManager) {
        System.out.println("\n--- Visitor Report ---");
        
        System.out.printf("%-4s %-10s %-25s %-15s %-30s %-35s %-30s %-30s %-15s%n", 
                          "ID", "Access ID", "Name", "Phone Number", 
                          "Purpose of Visit", "Patient Name", 
                          "Check-In Time", "Check-Out Time", "Block Access");

        if (visitorList.isEmpty()) {
            System.out.println("No visitors registered yet.");
        } else {
            for (Visitor visitor : visitorList) {
                VisitLog visitLog = visitLogManager.findLogById(visitor.getVisitorId());

                String checkInTime = (visitLog != null) ? visitLog.getEntryTime() : "N/A";
                String checkOutTime = (visitLog != null) ? visitLog.getExitTime() : "N/A";
                
                String patientRoomNumber = getPatientRoomNumber(visitor.getPatientName());
                String blockAccess = getBlockAccess(patientRoomNumber);

                System.out.printf("%-4d %-10s %-25s %-15s %-30s %-35s %-30s %-30s %-15s%n",
                                  visitor.getVisitorId(),
                                  visitor.getAccessId(),
                                  visitor.getName(),
                                  visitor.getPhoneNumber(),
                                  visitor.getPurposeOfVisit(),
                                  visitor.getPatientName(),
                                  checkInTime,
                                  checkOutTime,
                                  blockAccess);
            }
        }
    }

    private static String getBlockAccess(String patientRoomNumber) {
        if (patientRoomNumber == null || patientRoomNumber.isEmpty()) {
            return "N/A";
        }
        
        int roomNum;
        try {
            roomNum = Integer.parseInt(patientRoomNumber);
        } catch (NumberFormatException e) {
            return "Invalid";
        }

        if (roomNum >= 100 && roomNum <= 110) {
            return "Block A";
        } else if (roomNum >= 111 && roomNum <= 120) {
            return "Block B";
        } else if (roomNum >= 121 && roomNum <= 130) {
            return "Block C";
        } else {
            return "Restricted Area";
        }
    }

    private static String getPatientRoomNumber(String patientName) {
        for (Patient patient : Patient.createDummyPatients()) { 
            if (patient.getName().equalsIgnoreCase(patientName)) {
                return patient.getRoomNumber();
            }
        }
        
        return null;
    }
}
