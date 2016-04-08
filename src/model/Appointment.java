package model;

import java.util.Date;

public class Appointment implements Comparable<Appointment>{
	private Date start;
	private Date finish;
	
	public Appointment(Date start, Date finish){
		this.start = start;
		this.finish = finish;
	}
	
	public Date getStartDate(){
		return start;
	}
	
	public Date getFinishDate(){
		return finish;
	}
	
	public String toString(){
		return start.toString() + " to " + finish.toString();
	}

	@Override
	public int compareTo(Appointment o) {
		return start.compareTo(o.start);
	}
}
