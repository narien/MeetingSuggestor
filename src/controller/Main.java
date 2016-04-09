package controller;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import viewer.MeetingSuggesterGUI;
import model.Appointment;
import model.User;
import model.UserDatabase;

public class Main {
	private static UserDatabase udb;
	private static MeetingSuggesterGUI window;
	
	public static void main(String args[]){
		udb = new UserDatabase();
		initilizeDB();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MeetingSuggesterGUI(udb);
					window.visible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}

	public static void initilizeDB(){
		ArrayList<String[]> appointments = new ArrayList<String[]>();

		//Adds the users to database, saves appointments for later, freebusy hard coded for ease of use.
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File("freebusy.txt")));
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
			parseAppointment(sdf, s);
		}
	}

	private static void parseAppointment(SimpleDateFormat sdf, String[] s) {
		Date start = null;
		Date finish = null;
		Appointment app = null;
		try {
			start = sdf.parse(s[1]);
			finish = sdf.parse(s[2]);
			app = new Appointment(start, finish);
		} catch (ParseException e) {
			return;
		}
		if(app != null){
			udb.get(s[0]).addAppointment(app);
		}
	}
}