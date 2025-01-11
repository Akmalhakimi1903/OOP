package Project;

import java.util.List;

public class Report {

    public static void displayAllVisitors(List<Visitor> visitorList, VisitLogManager visitLogManager) {
        System.out.println("\n--- Visitor Report ---");
        
        System.out.printf("%-4s %-10s %-25s %-15s %-18s %-15s %-20s %-20s%n", 
                          "ID", "Access ID", "Name", "Phone Number", 
                          "Purpose of Visit", "Patient Name", 
                          "Check-In Time", "Check-Out Time");

        if (visitorList.isEmpty()) {
            System.out.println("No visitors registered yet.");
        } else {
            for (Visitor visitor : visitorList) {
                VisitLog visitLog = visitLogManager.findLogById(visitor.getVisitorId());

                String checkInTime = (visitLog != null) ? visitLog.getEntryTime() : "N/A";
                String checkOutTime = (visitLog != null) ? visitLog.getExitTime() : "N/A";

                System.out.printf("%-4d %-10s %-25s %-15s %-18s %-15s %-20s %-20s%n",
                                  visitor.getVisitorId(),
                                  visitor.getAccessId(),
                                  visitor.getName(),
                                  visitor.getPhoneNumber(),
                                  visitor.getPurposeOfVisit(),
                                  visitor.getPatientName(),
                                  checkInTime,
                                  checkOutTime);
            }
        }
    }
}
