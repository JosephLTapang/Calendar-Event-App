//////////////////////////////////////////////////////////////////////////
//Project Name: CalendarGUIMain.java                                    //
//Team Yangtze: Johnny Le, Christian Takemoto, Joseph Tapang            //
//Date: 12Aug2014                                                       //
//Description: CalendarGUIMain.java allows the user to input data       //
//relating to events that they wish to save to a calendar program such  //
//as Google Calendar.                                                   //
//////////////////////////////////////////////////////////////////////////

import javax.swing.*;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
	private JTextField textfield4;
	private JTextField textfield5;

	private int pri;
	private String classi;
	private String recur;

	//new variables for calendar module
	private String formattedDate;
	private Date startD;
	private JDatePickerImpl dPicker;

	//Constructor that adds buttons, labels, and text fields to the window.
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

		label3 = new JLabel("Event Date");
		add(label3);
		UtilDateModel udm = new UtilDateModel();
		JDatePanelImpl dPanel = new JDatePanelImpl(udm);
		dPicker = new JDatePickerImpl(dPanel);
		add(dPicker);
		startD = (Date)dPicker.getModel().getValue();

		label4 = new JLabel("Time Start (24hr)");
		add(label4);
		textfield4 = new JTextField(15);
		add(textfield4);

		label5 = new JLabel("Time End (24hr)");
		add(label5);
		textfield5 = new JTextField(15);
		add(textfield5);

		// Priority High, Medium, Low
		label6 = new JLabel("Priority");
		add(label6);
		pri = 5;
		radioButton1 = new JRadioButton("High");
		radioButton2 = new JRadioButton("Normal", true);
		radioButton3 = new JRadioButton("Low");
		add(radioButton1);
		add(radioButton2);
		add(radioButton3);
		groupButton1();

		//Recurring events: Not recurring, daily, weekly, monthly, yearly.
		label7 = new JLabel("Recurring Event");
		add(label7);
		recur = "NOT RECURRING";
		radioButton4 = new JRadioButton("Not Recurring", true);
		radioButton5 = new JRadioButton("Daily");
		radioButton9 = new JRadioButton("Weekly");
		radioButton10 = new JRadioButton("Monthly");
		radioButton11 = new JRadioButton("Yearly");
		add(radioButton4);
		add(radioButton5);
		add(radioButton9);
		add(radioButton10);
		add(radioButton11);
		groupButton2();

		//Classification: Public, Private, Confidential.
		label8 = new JLabel("Event Visibility");
		add(label8);
		classi = "PUBLIC";
		radioButton6 = new JRadioButton("Public", true);
		radioButton7 = new JRadioButton("Private");
		radioButton8 = new JRadioButton("Confidential");
		add(radioButton6);
		add(radioButton7);
		add(radioButton8);
		groupButton3();

		//Button that either creates a file or returns user input errors.
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

	//Checks valid time if user inputs time that is invalid then return false else return true.
	public static boolean checkValidTime2(String userInput){
		if(!userInput.matches("[0-9]+")|| userInput.length() < 4 || Integer.parseInt(userInput) > 2400)
		{
			return false;
		}
		else{
			return true;
		}
	}
	//Checks user input date for correct format such as: YYYYMMDD format, if the date is a future time or day,
	//and if the user input an invalid day such as Feb 30. 
	public static int checkValidDate(Date eDate){
		Date current = new Date();

		if(eDate.compareTo(current)<0)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	// Code relating to the creation of file after user is done and presses the
	// button.
	public void actionPerformed(ActionEvent e) {
		String locName = textfield2.getText();
		String summary = textfield1.getText();

		startD = new Date();
		startD = (Date)dPicker.getModel().getValue();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		formattedDate = df.format(startD);

		String endt = textfield5.getText();
		String startt = textfield4.getText();
		TimeZone tz;
		StringBuilder sb;
		BufferedWriter writer = null;
		int locSet = 1;

		tz = Calendar.getInstance().getTimeZone();
		sb = new StringBuilder();

		if(locName.trim() == "")
		{
			locSet = 0;
		}

		//The following six conditionals provide pop ups for errors that can occur when the user is inputting time and dates.
		if(checkValidTime2(startt) == false || checkValidTime2(endt) == false){
			JFrame frame = new JFrame("");
			JOptionPane.showMessageDialog(frame, "ERROR: Time format is 24hr HHMM", "Time Input Error", JOptionPane.ERROR_MESSAGE);
		}
		else if(checkValidEndTime(startt, endt) == false){
			JFrame frame = new JFrame("");
			JOptionPane.showMessageDialog(frame, "ERROR: End time is before start time.", "Time Input Error", JOptionPane.ERROR_MESSAGE);
		}
		else if(checkValidDate(startD) == 1){
			JFrame frame = new JFrame("");
			JOptionPane.showMessageDialog(frame, "ERROR: Date precedes present.", "Date Input Error", JOptionPane.ERROR_MESSAGE);
		}
		else{

			//Creates a file with the users event and daily recurrence.
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
				System.out.println("DTSTART:" + formattedDate + "T" + startt + "00");
				sb.append("DTSTART:" + formattedDate + "T" + startt + "00\n");
				System.out.println("DTEND:" + formattedDate + "T" + endt + "00");
				sb.append("DTEND:" + formattedDate + "T" + endt + "00\n");
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
			//Creates a file with the users event and weekly recurrence.
			else if(recur.equals("WEEKLY")){

				Calendar userDate = Calendar.getInstance();
				userDate.set(Integer.parseInt(formattedDate.substring(0,4)), Integer.parseInt(formattedDate.substring(4,6))-1,Integer.parseInt(formattedDate.substring(6)));
				SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.E");                    
				String theDate = format.format(userDate.getTime());
				String dayOfTheWeek = theDate.substring(8);
				String shortDayOfTheWeek;

				//Set of conditionals that abbreviate the input day of the week to DD format.This allows Google 
				//calendar to recognize the day when determining the recurrence from the .ics file.

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
				System.out.println("DTSTART:" + formattedDate + "T" + startt + "00");
				sb.append("DTSTART:" + formattedDate + "T" + startt + "00\n");
				System.out.println("DTEND:" + formattedDate + "T" + endt + "00");
				sb.append("DTEND:" + formattedDate + "T" + endt + "00\n");
				if(locSet == 1){
					System.out.println("LOCATION:" + locName);
					sb.append("LOCATION:" + locName + "\n");
				}
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
			//Creates a file with the users event and monthly recurrence.
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
				System.out.println("DTSTART:" + formattedDate + "T" + startt + "00");
				sb.append("DTSTART:" + formattedDate + "T" + startt + "00\n");
				System.out.println("DTEND:" + formattedDate + "T" + endt + "00");
				sb.append("DTEND:" + formattedDate + "T" + endt + "00\n");
				if(locSet == 1){
					System.out.println("LOCATION:" + locName);
					sb.append("LOCATION:" + locName + "\n");
				}
				System.out.println("SUMMARY:" + summary);
				sb.append("SUMMARY:" + summary + "\n");
				System.out.println("PRIORITY:"+pri);
				sb.append("PRIORITY:"+pri+"\n");
				System.out.println("CLASS:"+classi);
				sb.append("CLASS:"+classi+"\n");
				System.out.println("RRULE:FREQ="+recur+";"+"BYMONTHDAY="+formattedDate.substring(6));
				sb.append("RRULE:FREQ="+recur+"\n"+"BYMONTHDAY="+formattedDate.substring(6));
				System.out.println("END:VEVENT");
				sb.append("END:VEVENT\n");
				System.out.println("END:VCALENDAR");
				sb.append("END:VCALENDAR\n");
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
			//Creates a file with the users event and yearly recurrence.
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
				System.out.println("DTSTART:" + formattedDate + "T" + startt + "00");
				sb.append("DTSTART:" + formattedDate + "T" + startt + "00\n");
				System.out.println("DTEND:" + formattedDate + "T" + endt + "00");
				sb.append("DTEND:" + formattedDate + "T" + endt + "00\n");
				if(locSet == 1){
					System.out.println("LOCATION:" + locName);
					sb.append("LOCATION:" + locName + "\n");
				}
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
			//Creates a file with the users event and no recurrence.
			else
			{
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
				System.out.println("DTSTART:" + formattedDate + "T" + startt + "00");
				sb.append("DTSTART:" + formattedDate + "T" + startt + "00\n");
				System.out.println("DTEND:" + formattedDate + "T" + endt + "00");
				sb.append("DTEND:" + formattedDate + "T" + endt + "00\n");
				if(locSet == 1){
					System.out.println("LOCATION:" + locName);
					sb.append("LOCATION:" + locName + "\n");
				}
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
	//Private handler class for priority radio buttons.
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
	//Private handler class for classification radio buttons.
	private class HandlerClass2 implements ItemListener
	{
		private String classification;
		public HandlerClass2(String s){
			classification = s;
		}
		@Override
		public void itemStateChanged(ItemEvent event) {
			if(classification.equals("Private")){
				classi = "PRIVATE";
			}
			else if(classification.equals("Confidential")){
				classi = "CONFIDENTIAL";
			}
		}
	}
	//Private handler class for recurrence radio buttons.
	private class HandlerClass3 implements ItemListener
	{
		private String recurrence;
		public HandlerClass3(String str){
			recurrence = str;
		}
		@Override
		public void itemStateChanged(ItemEvent event) {
			if(recurrence.equals("Daily")){
				recur = "DAILY";
			}
			else if(recurrence.equals("Weekly")){
				recur = "WEEKLY";
			}
			else if(recurrence.equals("Monthly")){
				recur = "MONTHLY";
			}
			else if(recurrence.equals("Yearly")){
				recur = "YEARLY";
			}
		}
	}
	//Main method begins.
	public static void main(String args[]) {
		CalendarGUIMain gui = new CalendarGUIMain();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(400, 700);
		gui.setVisible(true);
		gui.setTitle("Calendar Event Generator");
	}
	//Main method ends.
}