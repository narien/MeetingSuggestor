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
		
		cal.setTime(intervalFirst); 
		intervalFirst = normalizeMeetingStart(cal); //Making sure meetings start on every half hour without bothering the user
		
		while(intervalFirst.compareTo(intervalLast) <= 0){
			cal.setTime(intervalFirst);
			cal.add(Calendar.MINUTE, length);
			
			boolean meetingEndGood = false; //Making sure meetings do not go past 17.00
			if(cal.get(Calendar.HOUR_OF_DAY) < 17 || (cal.get(Calendar.HOUR_OF_DAY) == 17 && cal.get(Calendar.MINUTE) == 0)){
				meetingEndGood = true;
			}
			
			Date temp = cal.getTime();
			
			cal.setTime(intervalFirst); //resetting it for use in if statement as well as be ready to increment 30 min
			
			if(cal.get(Calendar.HOUR_OF_DAY) > 7 && cal.get(Calendar.HOUR_OF_DAY) < 17 && meetingEndGood){
				apps.add(new Appointment(intervalFirst, temp));
			}	
			cal.add(Calendar.MINUTE, 30);
			intervalFirst = cal.getTime();
		}
		
		for(String s : participants){
			apps = udb.get(s).reduceToSuitableSlots(apps);
		}
	}

	private Date normalizeMeetingStart(Calendar cal) {
		Date intervalFirst;
		if(cal.get(Calendar.MINUTE) > 0 && cal.get(Calendar.MINUTE) < 30){
			cal.set(Calendar.MINUTE, 30);
		} else if (cal.get(Calendar.MINUTE) > 30){
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)+1);
		}
		intervalFirst = cal.getTime();
		return intervalFirst;
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
