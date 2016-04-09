package viewer;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import model.Appointment;
import model.AppointmentFinder;
import model.User;
import model.UserDatabase;

public class MeetingSuggesterGUI {

	private JFrame frame;
	private JTextField txtfIntervalStart;
	private JTextField txtfIntervalFinish;
	private JTextField txtfDuration;
	private JList<User> jUserList;
	private JList<Appointment> results;
	
	private UserDatabase udb;

	/**
	 * Create the gui.
	 */
	public MeetingSuggesterGUI(UserDatabase udb) {
		this.udb = udb;
		initialize(udb);
	}
	
	public void visible(boolean b) {
		frame.setVisible(b);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(UserDatabase udb) {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblMeetingInterval = new JLabel("Start Interval");
		lblMeetingInterval.setBounds(6, 12, 101, 16);
		frame.getContentPane().add(lblMeetingInterval);
		
		txtfIntervalStart = new JTextField("M/d/yyyy h:m:s AM/PM");
		txtfIntervalStart.setBounds(148, 6, 215, 28);
		frame.getContentPane().add(txtfIntervalStart);
		txtfIntervalStart.setColumns(10);
		
		JLabel label = new JLabel("-");
		label.setBounds(365, 12, 10, 16);
		frame.getContentPane().add(label);
		
		txtfIntervalFinish = new JTextField("M/d/yyyy h:m:s AM/PM");
		txtfIntervalFinish.setBounds(375, 6, 215, 28);
		frame.getContentPane().add(txtfIntervalFinish);
		txtfIntervalFinish.setColumns(10);
		
		JLabel lblMeetingDuration = new JLabel("Duration (min)");
		lblMeetingDuration.setBounds(6, 45, 130, 16);
		frame.getContentPane().add(lblMeetingDuration);
		
		txtfDuration = new JTextField("30");
		txtfDuration.setBounds(148, 39, 134, 28);
		frame.getContentPane().add(txtfDuration);
		txtfDuration.setColumns(10);
		
		JScrollPane scrollPaneUser = new JScrollPane();
		scrollPaneUser.setBounds(6, 73, 450, 199);
		jUserList = new JList<User>(udb.toListModel());
		jUserList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPaneUser.setViewportView(jUserList);
		frame.getContentPane().add(scrollPaneUser);
		
		JButton btnSuggestTime = new JButton("Suggest Time");
		btnSuggestTime.setBounds(573, 243, 117, 29);
		frame.getContentPane().add(btnSuggestTime);
				
		JScrollPane spResults = new JScrollPane();
		spResults.setBounds(480, 73, 210, 170);
		results = new JList<Appointment>();
		spResults.setViewportView(results);
		frame.getContentPane().add(spResults);
		
		
		/**
		 * Will respond on button click
		 */
		btnSuggestTime.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent evt) {
		           findSlots();
		      }
		});
	}

	private void findSlots(){
		SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy h:mm:ss aa", Locale.US);
		Date initial; 		//initial = new Date("3/13/2015 8:00:00 AM");
		Date finite;		//finite = new Date("3/13/2015 5:00:00 PM");
		int duration;

		try {
			initial = sdf.parse(txtfIntervalStart.getText());
		} catch (ParseException e) {
			infoBox("Incorrect interval start, please use format: M/d/yyyy h:m:s AM/PM\n Ex: 3/13/2015 8:00:00 AM", "Interval Error");
			return;
		}
		try {
			finite = sdf.parse(txtfIntervalFinish.getText());
		} catch (ParseException e) {
			infoBox("Incorrect interval end, please use format: M/d/yyyy h:m:s AM/PM\n Ex: 3/13/2015 5:00:00 PM", "Interval Error");
			return;
		}
		try {
			duration = Integer.parseInt(txtfDuration.getText());

		} catch (java.lang.NumberFormatException e) {
			infoBox("Incorrect duration format, please enter the amount\n of minutes the meeting should be scheduled for.", "Duration error");
			return;
		}
		if(initial.compareTo(finite) > 0){
			infoBox("Incorrect Interval selection, please enter a functional interval.", "Interval error");
			return;
		}
		
		List<User> list = jUserList.getSelectedValuesList();
		HashSet<String> participants = new HashSet<String>();
		
		for(User u : list)
			participants.add(u.getId());
		
		if(participants.size() == 0){
			infoBox("No participants selected.", "Select Participants");
			return;
		}
		
		results.setModel(new AppointmentFinder(initial, finite, duration, participants, udb).toListModel());
	}
	
	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
