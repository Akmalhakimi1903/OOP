package Project;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VisitLog {
    private int visitorId; 
    private String entryTime; 
    private String exitTime;  

    public VisitLog(int visitorId) { 
      this.visitorId=visitorId; 
      this.entryTime=null;  
      this.exitTime=null;  
   }

   public int getVisitorId() { return visitorId; }

   public String getEntryTime() { return entryTime; }
   
   public String getExitTime() { return exitTime; }

   public void recordEntry() { 
      SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
      entryTime=formatter.format(new Date());  
   }

   public void recordExit() { 
      SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
      exitTime=formatter.format(new Date());  
   }

   @Override 
   public String toString() { 
      return"Visit Log [Visitor ID="+visitorId+", Entry Time="+entryTime+", Exit Time="+exitTime+"]";  
   } 
}
