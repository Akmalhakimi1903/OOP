package Project;

public class Visitor extends Person {
    private int visitorId;
    private String accessId;
    private String purposeOfVisit; // This field holds the purpose of the visit
    private String patientName; 
    private boolean checkedIn;

    public Visitor(int visitorId, String accessId, String name, String phoneNumber,
                   String purposeOfVisit, String patientName, boolean checkedIn) {
        super(name, phoneNumber); // Call to Person constructor
        this.visitorId = visitorId;
        this.accessId = accessId;
        this.purposeOfVisit = purposeOfVisit; 
        this.patientName = patientName; 
        this.checkedIn = checkedIn; 
    }

    public int getVisitorId() { return visitorId; }
    public String getAccessId() { return accessId; }
    
    public String getPatientName() { return patientName; } // Getter for patient name
    public String getPurposeOfVisit() { return purposeOfVisit; } // Added method

    public boolean isCheckedIn() { return checkedIn; }

    public void checkIn() { checkedIn = true; }
    
    public void checkOut() { checkedIn = false; }

    public boolean isAccessAllowed(String patientRoomNumber) {
        int roomNum = Integer.parseInt(patientRoomNumber);

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
