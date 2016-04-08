package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class User {
	private String id;
	private String name;
	private ArrayList<Appointment> appointments;
	
	public User (String id, String name){
		this.id = id;
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
	
	public void printApps(){
				for(Appointment a : appointments)
			System.out.println(a.toString());
	}

	public HashSet<Appointment> reduceToSuitableSlots(HashSet<Appointment> apps) {
		//sortApps();
		HashSet<Appointment> iterator = new HashSet<Appointment>(apps);
		
		for(Appointment a : iterator){
			for(Appointment b : appointments){
				if (apps.contains(a) && a.collideWith(b)){
					apps.remove(a);
				}
			}
		}
		return apps;
	}
}
