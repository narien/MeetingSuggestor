package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;



public class AppointmentFinder {
	HashSet<Appointment> apps;
	
	public AppointmentFinder(Date intervalFirst, Date intervalLast, int length, 
			HashSet<String> participants, UserDatabase udb){
		apps = new HashSet<Appointment>();
		Calendar cal = Calendar.getInstance();

		while(!intervalFirst.equals(intervalLast)){
			cal.setTime(intervalFirst);
			cal.add(Calendar.MINUTE, length);
			Date temp = cal.getTime();
			
			apps.add(new Appointment(intervalFirst, temp));
			
			cal.setTime(intervalFirst);
			cal.add(Calendar.MINUTE, 30);
			intervalFirst = cal.getTime();
		}
		
		for(String s : participants){
			findSlotsForUser(s, udb);
		}
	}
	
	public void findSlotsForUser(String id, UserDatabase udb){
		apps = udb.get(id).reduceToSuitableSlots(apps);
	}
	
	public HashSet<Appointment> getSet(){
		return apps;
	}
}
