package model;

import java.util.Date;

public class Appointment implements Comparable<Appointment>{
	private Date start;
	private Date finish;
	
	public Appointment(Date start, Date finish, String id){
		this.start = start;
		this.finish = finish;
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
