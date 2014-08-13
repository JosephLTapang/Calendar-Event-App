import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Locale;

public class CalendarGUIMain extends JFrame implements ActionListener {
	private JButton button1;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JRadioButton radioButton1;
	private JRadioButton radioButton2;
	private JRadioButton radioButton3;
	private JRadioButton radioButton4;
	private JRadioButton radioButton5;
	private JRadioButton radioButton6;
	private JRadioButton radioButton7;
	private JRadioButton radioButton8;
	private JRadioButton radioButton9;
	private JRadioButton radioButton10;
	private JRadioButton radioButton11;

	private JTextField textfield1;
	private JTextField textfield2;
	private JTextField textfield3;
	private JTextField textfield4;
	private JTextField textfield5;

	//Moved classification, recurrence, and priority variables to main scope
	private int pri;
	private String classi;
	private String recur;

	//Constructor adds buttons, labels, and text fields
	public CalendarGUIMain() {

		setLayout(new GridLayout(0, 1));

		label1 = new JLabel("Event Name");
		add(label1);
		textfield1 = new JTextField(15);
		add(textfield1);

		label2 = new JLabel("Event Location");
		add(label2);
		textfield2 = new JTextField(15);
		add(textfield2);

		label3 = new JLabel("Event Date (YYYYMMDD)");
		add(label3);
		textfield3 = new JTextField(15);
		add(textfield3);

		label4 = new JLabel("Event Time Start (24hr)");
		add(label4);
		textfield4 = new JTextField(15);
		add(textfield4);

		label5 = new JLabel("Event Time End (24hr)");
		add(label5);
		textfield5 = new JTextField(15);
		add(textfield5);

		// Priority High, Medium, Low
		label6 = new JLabel("Priority");
		add(label6);
		pri = 5;
		radioButton1 = new JRadioButton("HIGH");
		radioButton2 = new JRadioButton("NORMAL", true);
		radioButton3 = new JRadioButton("LOW");
		add(radioButton1);
		add(radioButton2);
		add(radioButton3);
		groupButton1();

		//Recurring event related.
		label7 = new JLabel("Recurring Event");
		add(label7);
		recur = "NOT RECURRING";
		radioButton4 = new JRadioButton("NOT RECURRING", true);
		radioButton5 = new JRadioButton("DAILY");
		radioButton9 = new JRadioButton("WEEKLY");
		radioButton10 = new JRadioButton("MONTHLY");
		radioButton11 = new JRadioButton("YEARLY");
		add(radioButton4);
		add(radioButton5);
		add(radioButton9);
		add(radioButton10);
		add(radioButton11);
		groupButton2();

		// public,private,confidential
		label8 = new JLabel("Event Visibility");
		add(label8);
		classi = "DEFAULT";
		radioButton6 = new JRadioButton("DEFAULT", true);
		radioButton7 = new JRadioButton("PUBLIC");
		radioButton8 = new JRadioButton("PRIVATE");
		add(radioButton6);
		add(radioButton7);
		add(radioButton8);
		groupButton3();

		// Button to create file once previous inputs are filled.
		button1 = new JButton("Create .ics");
		add(button1);
		button1.addActionListener(this);// Button is listening for event.
	}

	//The following three methods group related radial buttons together.
	public void groupButton1() {
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(radioButton1);
		bg1.add(radioButton2);
		bg1.add(radioButton3);
		radioButton1.addItemListener(new HandlerClass(1));
		radioButton2.addItemListener(new HandlerClass(5));
		radioButton3.addItemListener(new HandlerClass(9));
	}

	public void groupButton2() {
		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(radioButton4);
		bg2.add(radioButton5);
		bg2.add(radioButton9);
		bg2.add(radioButton10);
		bg2.add(radioButton11);
		radioButton4.addItemListener(new HandlerClass3(radioButton4.getText()));
		radioButton5.addItemListener(new HandlerClass3(radioButton5.getText()));
		radioButton9.addItemListener(new HandlerClass3(radioButton9.getText()));
		radioButton10.addItemListener(new HandlerClass3(radioButton10.getText()));
		radioButton11.addItemListener(new HandlerClass3(radioButton11.getText()));


	}

	public void groupButton3() {
		ButtonGroup bg3 = new ButtonGroup();
		bg3.add(radioButton6);
		bg3.add(radioButton7);
		bg3.add(radioButton8);
		radioButton6.addItemListener(new HandlerClass2(radioButton6.getText()));
		radioButton7.addItemListener(new HandlerClass2(radioButton7.getText()));
		radioButton8.addItemListener(new HandlerClass2(radioButton8.getText()));
	}


	//Checks if users end time is after start time, if it is then return true, else return false.
	//Compare startt and endt variables.
	public boolean checkValidEndTime(String startTime, String endTime){
		if(!endTime.matches("[0-9]+")|| endTime.length() < 4 || Integer.parseInt(endTime) > 2400 || Integer.parseInt(endTime) < Integer.parseInt(startTime))
		{
			return false;
		}
		else{
			return true;
		}
	}

	//Checks valid time if user inputs time that is crazy then return false else return true.
	public static boolean checkValidTime(String userInput){
		if(!userInput.matches("[0-9]+")|| userInput.length() < 4 || Integer.parseInt(userInput) > 2400)
		{
			return false;
		}
		else{
			return true;
		}
	}

	public static int checkValidDate(String date){//In form YYYYMMDD
		String formattedDate;
		Date current = new Date();
		System.out.println(current.toString());
		System.out.println(date.toString());

		if(!date.matches("[0-9]+") || date.length() != 8)
		{
			return 1;
		}

		formattedDate = date.substring(4, 6) + "/" + date.substring(6, 8) + "/" + date.substring(0, 4);// MM/DD/YYYY

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date testDate = null;

		try{
			testDate = sdf.parse(formattedDate);
		}
		catch(ParseException pe){
			return 2;
		}
		System.out.println("sdf: " + sdf.format(testDate));
		System.out.println("formattedDate: " + formattedDate);
		System.out.println(testDate);
		System.out.println(date);
		/*If the sdf.format(testDate) returns a different String than formattedDate string, the formatted date put into
		 * the testDate is incorrect in some way. Ex 12/32/2014 becomes 1/1/2015*/

		if(!(sdf.format(testDate).equals(formattedDate))){
			return 3;
		}	
		if(current.after(testDate)){
			return 4;
		}
		return 0;
	}
	// Code relating to the creation of file after user is done and presses the
	// button.
	public void actionPerformed(ActionEvent e) {
		String locName = textfield2.getText();
		String summary = textfield1.getText();
		String date = textfield3.getText();
		String endt = textfield5.getText();
		String startt = textfield4.getText();
		TimeZone tz;
		StringBuilder sb;
		BufferedWriter writer = null;

		tz = Calendar.getInstance().getTimeZone();
		sb = new StringBuilder();

		//Following six conditionals provide pop ups for errors that can occur when the user is inputting time and dates.
		if(checkValidEndTime(startt, endt) == false){
			JFrame frame = new JFrame("");
			JOptionPane.showMessageDialog(frame, "ERROR: End time is before start time.", "Time Input Error", JOptionPane.ERROR_MESSAGE);
		}
		else if(checkValidTime(startt) == false || checkValidTime(endt) == false){
			JFrame frame = new JFrame("");
			JOptionPane.showMessageDialog(frame, "ERROR: Time format is 24hr HHMM", "Time Input Error", JOptionPane.ERROR_MESSAGE);
		}
		else if(checkValidDate(date) == 1){
			JFrame frame = new JFrame("");
			JOptionPane.showMessageDialog(frame, "ERROR: Date format is YYYYMMDD", "Date Input Error", JOptionPane.ERROR_MESSAGE);
		}
		else if(checkValidDate(date) == 2){
			JFrame frame = new JFrame("");
			JOptionPane.showMessageDialog(frame, "ERROR: Date format is YYYYMMDD", "Date Input Error", JOptionPane.ERROR_MESSAGE);
		}
		else if(checkValidDate(date) == 3){
			JFrame frame = new JFrame("");
			JOptionPane.showMessageDialog(frame, "ERROR: Invalid date, i.e. Feb 35th.", "Date Input Error", JOptionPane.ERROR_MESSAGE);
		}
		else if(checkValidDate(date) == 4){
			JFrame frame = new JFrame("");
			JOptionPane.showMessageDialog(frame, "ERROR: Date precedes present.", "Date Input Error", JOptionPane.ERROR_MESSAGE);
		}
		else{

			if(recur.equals("DAILY")){
				System.out.println("Event to be added:\n");
				System.out.println("BEGIN:VCALENDAR");
				sb.append("BEGIN:VCALENDAR\n");
				System.out.println("PROID:-//Team Yangtze//yzcalendar 0.1//EN");
				sb.append("PROID:-//Team Yangtze//yzcalendar 0.1//EN\n");
				System.out.println("VERSION:1.0");
				sb.append("VERSION:1.0\n");
				System.out.println("BEGIN:VTIMEZONE");
				sb.append("BEGIN:VTIMEZONE\n");
				System.out.println("TZID:" + tz.getID());
				sb.append("TZID:" + tz.getID() + "\n");
				System.out.println("END:VTIMEZONE");
				sb.append("END:VTIMEZONE\n");
				System.out.println("BEGIN:VEVENT");
				sb.append("BEGIN:VEVENT\n");
				System.out.println("DTSTART:" + date + "T" + startt + "00Z");
				sb.append("DTSTART:" + date + "T" + startt + "00Z\n");
				System.out.println("DTEND:" + date + "T" + endt + "00Z");
				sb.append("DTEND:" + date + "T" + endt + "00Z\n");
				System.out.println("LOCATION:" + locName);
				sb.append("LOCATION:" + locName + "\n");
				System.out.println("SUMMARY:" + summary);
				sb.append("SUMMARY:" + summary + "\n");
				System.out.println("PRIORITY:"+pri);
				sb.append("PRIORITY:"+pri+"\n");
				System.out.println("CLASS:"+classi);
				sb.append("CLASS:"+classi+"\n");
				System.out.println("RRULE:FREQ="+recur);
				sb.append("RRULE:FREQ="+recur+"\n");
				System.out.println("END:VEVENT");
				sb.append("END:VEVENT\n");
				System.out.println("END:VCALENDAR");
				sb.append("END:VCALENDAR\n");

				// IOException error may not apply with text fields and buttons.
				try {
					writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
							"yangtze.ics"), "utf-8"));
					writer.write(sb.toString());
				} catch (IOException exception) {
					System.out.println("Error writing to file");
				} finally {
					try {
						writer.close();
					} catch (Exception e2) {
						System.out.println("Error writing to file");
					}
				}
			}
			else if(recur.equals("WEEKLY")){
				Calendar userDate = Calendar.getInstance();
				userDate.set(Integer.parseInt(date.substring(0,4)), Integer.parseInt(date.substring(4,6))-1,Integer.parseInt(date.substring(6)));// second argument is -1 since months in this classes array start from 0.
				SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.E");
				
				String theDate = format.format(userDate.getTime());
				String dayOfTheWeek = theDate.substring(8);
				String shortDayOfTheWeek;
				
				if(dayOfTheWeek.equals("Sun")){
					shortDayOfTheWeek = "SU";
				}
				else if(dayOfTheWeek.equals("Mon")){
					shortDayOfTheWeek = "MO";
				}
				else if(dayOfTheWeek.equals("Tue")){
					shortDayOfTheWeek = "TU";
				}
				else if(dayOfTheWeek.equals("Wed")){
					shortDayOfTheWeek = "WE";
				}
				else if(dayOfTheWeek.equals("Thu")){
					shortDayOfTheWeek = "TH";
				}
				else if(dayOfTheWeek.equals("Fri")){
					shortDayOfTheWeek = "FR";
				}
				else{
					shortDayOfTheWeek = "SA";
				}
			
				System.out.println("Event to be added:\n");
				System.out.println("BEGIN:VCALENDAR");
				sb.append("BEGIN:VCALENDAR\n");
				System.out.println("PROID:-//Team Yangtze//yzcalendar 0.1//EN");
				sb.append("PROID:-//Team Yangtze//yzcalendar 0.1//EN\n");
				System.out.println("VERSION:1.0");
				sb.append("VERSION:1.0\n");
				System.out.println("BEGIN:VTIMEZONE");
				sb.append("BEGIN:VTIMEZONE\n");
				System.out.println("TZID:" + tz.getID());
				sb.append("TZID:" + tz.getID() + "\n");
				System.out.println("END:VTIMEZONE");
				sb.append("END:VTIMEZONE\n");
				System.out.println("BEGIN:VEVENT");
				sb.append("BEGIN:VEVENT\n");
				System.out.println("DTSTART:" + date + "T" + startt + "00Z");
				sb.append("DTSTART:" + date + "T" + startt + "00Z\n");
				System.out.println("DTEND:" + date + "T" + endt + "00Z");
				sb.append("DTEND:" + date + "T" + endt + "00Z\n");
				System.out.println("LOCATION:" + locName);
				sb.append("LOCATION:" + locName + "\n");
				System.out.println("SUMMARY:" + summary);
				sb.append("SUMMARY:" + summary + "\n");
				System.out.println("PRIORITY:"+pri);
				sb.append("PRIORITY:"+pri+"\n");
				System.out.println("CLASS:"+classi);
				sb.append("CLASS:"+classi+"\n");
				System.out.println("RRULE:FREQ="+recur+";"+"BYDAY="+shortDayOfTheWeek+"\n");
				sb.append("RRULE:FREQ="+recur+";"+"BYDAY="+shortDayOfTheWeek+"\n");
				System.out.println("END:VEVENT");
				sb.append("END:VEVENT\n");
				System.out.println("END:VCALENDAR");
				sb.append("END:VCALENDAR\n");

				// IOException error may not apply with text fields and buttons.
				try {
					writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
							"yangtze.ics"), "utf-8"));
					writer.write(sb.toString());
				} catch (IOException exception) {
					System.out.println("Error writing to file");
				} finally {
					try {
						writer.close();
					} catch (Exception e2) {
						System.out.println("Error writing to file");
					}
				}
			}
			else if(recur.equals("MONTHLY")){
				System.out.println("Event to be added:\n");
				System.out.println("BEGIN:VCALENDAR");
				sb.append("BEGIN:VCALENDAR\n");
				System.out.println("PROID:-//Team Yangtze//yzcalendar 0.1//EN");
				sb.append("PROID:-//Team Yangtze//yzcalendar 0.1//EN\n");
				System.out.println("VERSION:1.0");
				sb.append("VERSION:1.0\n");
				System.out.println("BEGIN:VTIMEZONE");
				sb.append("BEGIN:VTIMEZONE\n");
				System.out.println("TZID:" + tz.getID());
				sb.append("TZID:" + tz.getID() + "\n");
				System.out.println("END:VTIMEZONE");
				sb.append("END:VTIMEZONE\n");
				System.out.println("BEGIN:VEVENT");
				sb.append("BEGIN:VEVENT\n");
				System.out.println("DTSTART:" + date + "T" + startt + "00Z");
				sb.append("DTSTART:" + date + "T" + startt + "00Z\n");
				System.out.println("DTEND:" + date + "T" + endt + "00Z");
				sb.append("DTEND:" + date + "T" + endt + "00Z\n");
				System.out.println("LOCATION:" + locName);
				sb.append("LOCATION:" + locName + "\n");
				System.out.println("SUMMARY:" + summary);
				sb.append("SUMMARY:" + summary + "\n");
				System.out.println("PRIORITY:"+pri);
				sb.append("PRIORITY:"+pri+"\n");
				System.out.println("CLASS:"+classi);
				sb.append("CLASS:"+classi+"\n");
				System.out.println("RRULE:FREQ="+recur+";"+"BYMONTHDAY="+date.substring(6));
				sb.append("RRULE:FREQ="+recur+"\n"+"BYMONTHDAY="+date.substring(6));
				System.out.println("END:VEVENT");
				sb.append("END:VEVENT\n");
				System.out.println("END:VCALENDAR");
				sb.append("END:VCALENDAR\n");

				// IOException error may not apply with text fields and buttons.
				try {
					writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
							"yangtze.ics"), "utf-8"));
					writer.write(sb.toString());
				} catch (IOException exception) {
					System.out.println("Error writing to file");
				} finally {
					try {
						writer.close();
					} catch (Exception e2) {
						System.out.println("Error writing to file");
					}
				}
			}
			else if(recur.equals("YEARLY")){
				System.out.println("Event to be added:\n");
				System.out.println("BEGIN:VCALENDAR");
				sb.append("BEGIN:VCALENDAR\n");
				System.out.println("PROID:-//Team Yangtze//yzcalendar 0.1//EN");
				sb.append("PROID:-//Team Yangtze//yzcalendar 0.1//EN\n");
				System.out.println("VERSION:1.0");
				sb.append("VERSION:1.0\n");
				System.out.println("BEGIN:VTIMEZONE");
				sb.append("BEGIN:VTIMEZONE\n");
				System.out.println("TZID:" + tz.getID());
				sb.append("TZID:" + tz.getID() + "\n");
				System.out.println("END:VTIMEZONE");
				sb.append("END:VTIMEZONE\n");
				System.out.println("BEGIN:VEVENT");
				sb.append("BEGIN:VEVENT\n");
				System.out.println("DTSTART:" + date + "T" + startt + "00Z");
				sb.append("DTSTART:" + date + "T" + startt + "00Z\n");
				System.out.println("DTEND:" + date + "T" + endt + "00Z");
				sb.append("DTEND:" + date + "T" + endt + "00Z\n");
				System.out.println("LOCATION:" + locName);
				sb.append("LOCATION:" + locName + "\n");
				System.out.println("SUMMARY:" + summary);
				sb.append("SUMMARY:" + summary + "\n");
				System.out.println("PRIORITY:"+pri);
				sb.append("PRIORITY:"+pri+"\n");
				System.out.println("CLASS:"+classi);
				sb.append("CLASS:"+classi+"\n");
				System.out.println("RRULE:FREQ="+recur);
				sb.append("RRULE:FREQ="+recur+"\n");
				System.out.println("END:VEVENT");
				sb.append("END:VEVENT\n");
				System.out.println("END:VCALENDAR");
				sb.append("END:VCALENDAR\n");

				// IOException error may not apply with text fields and buttons.
				try {
					writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
							"yangtze.ics"), "utf-8"));
					writer.write(sb.toString());
				} catch (IOException exception) {
					System.out.println("Error writing to file");
				} finally {
					try {
						writer.close();
					} catch (Exception e2) {
						System.out.println("Error writing to file");
					}
				}
			}
			else{
				System.out.println("Event to be added:\n");
				System.out.println("BEGIN:VCALENDAR");
				sb.append("BEGIN:VCALENDAR\n");
				System.out.println("PROID:-//Team Yangtze//yzcalendar 0.1//EN");
				sb.append("PROID:-//Team Yangtze//yzcalendar 0.1//EN\n");
				System.out.println("VERSION:1.0");
				sb.append("VERSION:1.0\n");
				System.out.println("BEGIN:VTIMEZONE");
				sb.append("BEGIN:VTIMEZONE\n");
				System.out.println("TZID:" + tz.getID());
				sb.append("TZID:" + tz.getID() + "\n");
				System.out.println("END:VTIMEZONE");
				sb.append("END:VTIMEZONE\n");
				System.out.println("BEGIN:VEVENT");
				sb.append("BEGIN:VEVENT\n");
				System.out.println("DTSTART:" + date + "T" + startt + "00Z");
				sb.append("DTSTART:" + date + "T" + startt + "00Z\n");
				System.out.println("DTEND:" + date + "T" + endt + "00Z");
				sb.append("DTEND:" + date + "T" + endt + "00Z\n");
				System.out.println("LOCATION:" + locName);
				sb.append("LOCATION:" + locName + "\n");
				System.out.println("SUMMARY:" + summary);
				sb.append("SUMMARY:" + summary + "\n");
				System.out.println("PRIORITY:"+pri);
				sb.append("PRIORITY:"+pri+"\n");
				System.out.println("CLASS:"+classi);
				sb.append("CLASS:"+classi+"\n");
				System.out.println("END:VEVENT");
				sb.append("END:VEVENT\n");
				System.out.println("END:VCALENDAR");
				sb.append("END:VCALENDAR\n");

				// IOException error may not apply with text fields and buttons.
				try {
					writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
							"yangtze.ics"), "utf-8"));
					writer.write(sb.toString());
				} catch (IOException exception) {
					System.out.println("Error writing to file");
				} finally {
					try {
						writer.close();
					} catch (Exception e2) {
						System.out.println("Error writing to file");
					}
				}
			}
			
		}
	}
	//Private handler class for priority radio buttons
	private class HandlerClass implements ItemListener
	{
		private int priority;
		public HandlerClass(int p){
			priority = p;
		}
		@Override
		public void itemStateChanged(ItemEvent event) {
			pri = priority;

		}
	}

	//Private handler class for classification radio buttons
	private class HandlerClass2 implements ItemListener
	{
		private String classification;
		public HandlerClass2(String s){
			classification = s;
		}
		@Override
		public void itemStateChanged(ItemEvent event) {
			classi = classification;

		}
	}
	private class HandlerClass3 implements ItemListener
	{
		private String recurrence;
		public HandlerClass3(String str){
			recurrence = str;
		}
		@Override
		public void itemStateChanged(ItemEvent event) {
			recur = recurrence;

		}
	}
	public static void main(String args[]) {
		CalendarGUIMain gui = new CalendarGUIMain();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(400, 600);
		gui.setVisible(true);
		gui.setTitle("Calendar Event Generator");
	}
}
