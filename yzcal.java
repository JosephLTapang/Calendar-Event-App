import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.TimeZone;
public class yzcal {

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

	//Checks if users end time is after start time, if it is then return true, else return false.
	//Compare startt and endt variables.
	//Use asserts true and false.
	public static boolean checkValidEndTime(String startTime, String endTime){
		if(!endTime.matches("[0-9]+")|| endTime.length() < 4 || Integer.parseInt(endTime) > 2400 || Integer.parseInt(endTime) < Integer.parseInt(startTime))
		{
			return false;
		}
		else{
			return true;
		}
	}

	//Checks valid date if user inputs date that is crazy then return false else return true.
	//Use asserts true and false.
	public static boolean checkValidDate(String date){
		if(!date.matches("[0-9]+") || date.length() != 8)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException
	{
		boolean result;
		String locName;
		String summary;
		String date;
		String recur;
		String classi;
		int clnum;
		String endt;
		String startt;
		int priority;
		TimeZone tz;
		BufferedWriter writer = null;
		StringBuilder sb;
		// * = used for recurring events later
		String recEnd;//*
		int rec = 0;//*
		int rec2;//*

		Scanner scanna = new Scanner(System.in);

		System.out.println("Please enter a descriptive name for the new event");
		System.out.print("New Event: ");
		summary = scanna.nextLine().trim();
		while(summary.length() == 0)
		{
			System.out.println("Invalid input: A description is required");
			System.out.println("Please enter a descriptive name for the new event");
			System.out.print("New Event: ");
			summary = scanna.nextLine().trim();
		}

		System.out.print("Please enter a location for the event: ");
		locName = scanna.nextLine().trim();
		if(locName.length() == 0)
		{
			locName = "";
		}

		System.out.println("\nPlease select a priority for the event.");
		System.out.println("(Enter 1 for:) High");
		System.out.println("(Enter 2 for:) Normal");
		System.out.println("(Enter 3 for:) Low");
		System.out.print("\nPriority: ");
		priority = scanna.nextInt();
		scanna.nextLine();
		while(priority > 3 || priority < 1)
		{
			System.out.println("Invalid Selection");
			System.out.println("\nPlease select a priority for the event.");
			System.out.println("(Enter 1 for:) High");
			System.out.println("(Enter 2 for:) Normal");
			System.out.println("(Enter 3 for:) Low");
			System.out.print("\nPriority: ");
			priority = scanna.nextInt();
			scanna.nextLine();
		}
		if(priority == 2)
		{
			priority = 5;
		}
		else if(priority == 3)
		{
			priority = 9;
		}

		System.out.println("Please enter the date of the event");
		System.out.print("(YYYYMMDD): ");
		date = scanna.nextLine();
		result = checkValidDate(date);
		while(result != true)
		{
			System.out.println("Invalid date");
			System.out.print("date format YYYYMMDD:");
			date = scanna.nextLine();
			result = checkValidDate(date);
		}

		System.out.println("Please enter the START time of the event");
		System.out.print("(24h format, i.e. 1500): ");
		startt = scanna.nextLine();
		result = checkValidTime(startt);
		while(result != true){
			System.out.println("Invalid Time format");
			System.out.print("(24h format, i.e. 1500): ");
			startt = scanna.nextLine();
			result = checkValidTime(startt);
		}

		System.out.println("Please enter the END time of the event");
		System.out.print("(24h format, i.e. 1500): ");
		endt = scanna.nextLine();
		result = checkValidEndTime(startt, endt);
		while(result != true)
		{
			System.out.println("Invalid Time or format");
			System.out.print("(24h format, i.e. 1500 and must be after Start Time): ");
			endt = scanna.nextLine();
			result = checkValidEndTime(startt, endt);
		}

		System.out.print("Is this event recurring? (y/n): ");
		recur = scanna.nextLine();
		if(recur == "y" || recur == "Y")
		{
			System.out.println("Please choose a recurring event option");
			System.out.println("(1)Every Day");
			System.out.println("(2)Every Week");
			System.out.println("(3)Every Month");
			System.out.println("(4)Every Year");
			System.out.print("Your selection: ");
			rec2 = scanna.nextInt();
			scanna.nextLine();
			while(rec2 > 4 || rec2 < 1)
			{
				System.out.println("Invalid Selection");
				System.out.println("Please choose a recurring event option");
				System.out.println("(1)Every Day");
				System.out.println("(2)Every Week");
				System.out.println("(3)Every Month");
				System.out.println("(4)Every Year");
				System.out.print("Your selection: ");
				rec2 = scanna.nextInt();
				scanna.nextLine();
			}

			System.out.println("Please enter the END date for the recurring event");
			System.out.print("(or enter 'n' to never end): ");
			recEnd = scanna.nextLine();
			if(recEnd == "n" || recEnd == "N")
			{
				recEnd = "00000000";
			}
			while(!recEnd.matches("[0-9]+"))
			{
				System.out.println("Invalid date");
				System.out.print("date format YYYYMMDD:");
				recEnd = scanna.nextLine();
			}

			rec = 1;
		}
		else if(recur == "n" || recur == "N")
		{
			System.out.println("One time event.");
		}
		else
		{
			System.out.println("Invalid input: Set to one time event.");
		}

		System.out.println("\nHow would you like to classify this event?");
		System.out.println("(1)Public");
		System.out.println("(2)Private");
		System.out.println("(3)Confidential\n");
		System.out.print("Your Selection: ");
		clnum = scanna.nextInt();
		scanna.nextLine();
		while(clnum > 4 || clnum < 1)
		{
			System.out.println("Invalid Selection");
			System.out.println("\nHow would you like to classify this event?");
			System.out.println("(1)Public");
			System.out.println("(2)Private");
			System.out.println("(3)Confidential\n");
			System.out.print("Your Selection: ");
			clnum = scanna.nextInt();
			scanna.nextLine();
		}
		if(clnum == 3)
		{
			classi = "CONFIDENTIAL";
		}
		else if(clnum == 2)
		{
			classi = "PRIVATE";
		}
		else
		{
			classi = "PUBLIC";
		}

		tz = Calendar.getInstance().getTimeZone();
		sb = new StringBuilder();

		System.out.println("Event to be added:\n");
		System.out.println("BEGIN:VCALENDAR");
		sb.append("BEGIN:VCALENDAR\n");
		System.out.println("PROID:-//Team Yangtze//yzcalendar 0.1//EN");
		sb.append("PROID:-//Team Yangtze//yzcalendar 0.1//EN\n");
		System.out.println("VERSION:1.0");
		sb.append("VERSION:1.0\n");
		System.out.println("BEGIN:VTIMEZONE");
		sb.append("BEGIN:VTIMEZONE\n");
		System.out.println("TZID:"+tz.getID());  
		sb.append("TZID:"+tz.getID()+"\n");
		System.out.println("END:VTIMEZONE");
		sb.append("END:VTIMEZONE\n");
		System.out.println("BEGIN:VEVENT");
		sb.append("BEGIN:VEVENT\n");
		System.out.println("DTSTART:"+date+"T"+startt+"00Z");
		sb.append("DTSTART:"+date+"T"+startt+"00Z\n");
		System.out.println("DTEND:"+date+"T"+endt+"00Z");
		sb.append("DTEND:"+date+"T"+endt+"00Z\n");
		System.out.println("LOCATION:"+locName);
		sb.append("LOCATION:"+locName+"\n");
		System.out.println("SUMMARY:"+summary);
		sb.append("SUMMARY:"+summary+"\n");
		System.out.println("PRIORITY:"+priority);
		sb.append("PRIORITY:"+priority+"\n");
		System.out.println("CLASS:"+classi);
		sb.append("CLASS:"+classi+"\n");
		System.out.println("END:VEVENT");
		sb.append("END:VEVENT\n");
		System.out.println("END:VCALENDAR");
		sb.append("END:VCALENDAR\n");

		try
		{
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("yangtze.ics"), "utf-8")); 
			writer.write(sb.toString());
		}
		catch(IOException e)
		{
			System.out.println("Error writing to file");
		}
		finally
		{
			try
			{
				writer.close();
			}
			catch(Exception e2)
			{
				System.out.println("Error writing to file");
			}
		}
	}
}
