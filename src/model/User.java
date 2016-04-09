package model;

import java.util.ArrayList;
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
	
	public boolean addAppointment(Appointment app){
		return appointments.add(app);
	}
	
	public void printApps(){
				for(Appointment a : appointments)
			System.out.println(a.toString());
	}

	public HashSet<Appointment> reduceToSuitableSlots(HashSet<Appointment> apps) {
		HashSet<Appointment> iterator = new HashSet<Appointment>(apps);
		
		for(Appointment a : iterator){ //Should be inefficient, could be better to step through like a merge sort algorithm?
			for(Appointment b : appointments){
				if (apps.contains(a) && a.collideWith(b)){
					apps.remove(a);
				}
			}
		}
		return apps;
	}
	
	public String toString(){
		return name + "\n" + id;
	}

	public String getId() {
		return id;
	}
}
