package model;

import java.util.Date;
import java.util.HashSet;

public class Appointment implements Comparable<Appointment>{
	private Date start;
	private Date finish;
	private HashSet<String> participants;
	
	public Appointment(Date start, Date finish, String id){
		this.start = start;
		this.finish = finish;
		participants = new HashSet<String>();
		participants.add(id);
	}
	
	public boolean addParticipant(String id){
		if(participants.contains(id))
			return false;
		
		participants.add(id);
		return true;
	}
	
	public Date getStartDate(){
		return start;
	}
	
	public Date getFinishDate(){
		return finish;
	}

	@Override
	public int compareTo(Appointment o) {
		return start.toString().compareTo(o.getStartDate().toString());
	}
}
