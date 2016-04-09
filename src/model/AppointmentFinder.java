package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import javax.swing.DefaultListModel;

public class AppointmentFinder {
	private HashSet<Appointment> apps;
	
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
	
	private void findSlotsForUser(String id, UserDatabase udb){
		apps = udb.get(id).reduceToSuitableSlots(apps);
	}
	
	public HashSet<Appointment> getSet(){
		return apps;
	}
	
	public DefaultListModel<Appointment> toListModel() {
		DefaultListModel<Appointment> appModel = new DefaultListModel<Appointment>();
		ArrayList<Appointment> afList = new ArrayList<Appointment>();
		
		for(Appointment a : apps) //Onödigt steg om jag kan sortera en defaultListModel
			afList.add(a);
		Collections.sort(afList);

		for(Appointment a : afList){
			appModel.addElement(a);
		}
		if(appModel.isEmpty())
			appModel.addElement(new Appointment(null, null));
		
		return appModel;

	}
}
