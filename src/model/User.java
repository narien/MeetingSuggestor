package model;

import java.util.ArrayList;
import java.util.Collections;

public class User {
	private String name;
	private ArrayList<Appointment> appointments;
	
	public User (String name){
		this.name = name;
		appointments = new ArrayList<Appointment>();
	}
	
	public String getName(){
		return name;
	}
	
	public boolean addAppointment(Appointment app){
		if(appointments.add(app))
			return true;
		
		return false;
		
	}
	
	public void sortApps(){
		Collections.sort(appointments);
	}
	
	public void printApps(){
				for(Appointment a : appointments)
			System.out.println(a.toString());
	}

}
