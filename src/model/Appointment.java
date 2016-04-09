package model;

import java.util.Date;

public class Appointment implements Comparable<Appointment>{
	private Date start;
	private Date finish;
	
	public Appointment(Date start, Date finish){
		this.start = start;
		this.finish = finish;
	}
	
	public String toString(){
		if(start == null || finish == null)
			return "No available appointment";
		
		return start.toString();
	}

	public boolean collideWith(Appointment b) {
		if (start.compareTo(b.finish) < 0  && finish.compareTo(b.start) > 0){
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(Appointment o) {
		return start.compareTo(o.start);
	}
}
