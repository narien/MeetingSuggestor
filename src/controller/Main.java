package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import model.Appointment;
import model.AppointmentFinder;
import model.User;
import model.UserDatabase;

public class Main {
	private static UserDatabase udb;
	
	public static void main(String args[]){
		udb = new UserDatabase();
		initilizeDB();
		
		//test data
		Date initial = new Date("3/13/2015 8:00:00 AM");
		Date finite = new Date("3/13/2015 5:00:00 PM");
		int duration = 30;
		HashSet<String> participants = new HashSet<String>();
		participants.add("57646786307395936680161735716561753784");
		participants.add("57646786307395936680161735716561753785");

		
		AppointmentFinder af = new AppointmentFinder(initial, finite, duration, participants, udb);
		
		//test code
		ArrayList<Appointment> afList = new ArrayList<Appointment>();
		for(Appointment a : af.getSet())
			afList.add(a);
		Collections.sort(afList);
		for(Appointment a : afList)
			System.out.println(a.toString());
	}

	public static void initilizeDB(){
		ArrayList<String[]> appointments = new ArrayList<String[]>();

		//Adds the users to database, saves appointments for later
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File("freebusytest.txt")));
			String s = reader.readLine();
			while(s != null){
				String[]elements = s.split(";");
				
				if(elements.length == 2){
					udb.put(elements[0], new User(elements[0], elements[1]));
				} else if (elements.length == 4){
					appointments.add(elements);
				}
				s = reader.readLine();
			}
			reader.close();
		} catch(FileNotFoundException ex) {
			System.err.println("freebusy.txt could not be found.");
			System.exit(1);
		} catch(IOException ex) {
			System.err.println("Error while reading from freebusy.txt");
			System.exit(1);
		}
		
		//Input the appointments for each user
		SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy h:mm:ss aa", Locale.US);
		for(String[] s : appointments){
			Date start = null;
			Date finish = null;
			Appointment app = null;
			try {
				start = sdf.parse(s[1]);
				finish = sdf.parse(s[2]);
				app = new Appointment(start, finish);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			if(app != null){
				udb.get(s[0]).addAppointment(app);
			}
		}
		//udb.get("57646786307395936680161735716561753784").printApps();
	}
}





















