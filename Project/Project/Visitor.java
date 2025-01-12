package Project;

public class Visitor {
    private int visitorId;
    private String accessId;
    private String name;
    private String phoneNumber;
    private String purposeOfVisit;
    private String patientName; // Added field for patient's name
    private boolean checkedIn;

    public Visitor(int visitorId, String accessId, String name, String phoneNumber,
                   String purposeOfVisit, String patientName, boolean checkedIn) {
        this.visitorId = visitorId;
        this.accessId = accessId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.purposeOfVisit = purposeOfVisit; 
        this.patientName = patientName; 
        this.checkedIn = checkedIn; 
    }

    public int getVisitorId() { return visitorId; }
    public String getAccessId() { return accessId; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPurposeOfVisit() { return purposeOfVisit; }
    public String getPatientName() { return patientName; } 

    public boolean isCheckedIn() { return checkedIn; }

    public void checkIn() { checkedIn = true; }
    public void checkOut() { checkedIn = false; }

    public boolean isAccessAllowed(String patientRoomNumber) {
        // Example logic for allowing access based on room number
        int roomNum = Integer.parseInt(patientRoomNumber);
        
        // Define allowed ranges for room numbers
        if (roomNum >= 100 && roomNum <= 110) {
            return true; // Access granted for Block A
        } else if (roomNum >= 111 && roomNum <= 120) {
            return true; // Access granted for Block B
        } else if (roomNum >= 121 && roomNum <= 130) {
            return true; // Access granted for Block C
        }
        
        return false; // Access denied for other blocks
    }

    @Override
    public String toString() { 
        return "Visitor ID: " + visitorId + ", Name: " + name + ", Access ID: " + accessId +
               ", Checked In: " + checkedIn + ", Phone Number: " + phoneNumber +
               ", Purpose of Visit: " + purposeOfVisit + ", Patient Name: " + patientName; 
    }
}
