package Project;

import java.util.ArrayList;
import java.util.List;

public class VisitLogManager { 
	private List<VisitLog> visitLogs;

	public VisitLogManager() { 
	  visitLogs=new ArrayList<>();  
   }

	public void addVisitLog(VisitLog log) { visitLogs.add(log); }

	public VisitLog findLogById(int visitorId) { 
	  for (VisitLog log : visitLogs) { 
	     if (log.getVisitorId()==visitorId) { 
	         return log;  
	     }  
	  }  
	  return null;  
   }
}
