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
  
  
  public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException
  {
    String locName;
    String summary;
    String date;
    String recur;
    String classi;
    int clnum;
    int endt;
    int startt;
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
    summary = scanna.nextLine();
    
    System.out.print("Please enter a location for the event: ");
    locName = scanna.nextLine();
    
    System.out.println("\nPlease select a priority for the event.");
    System.out.println("(Enter 1 for:) High Priority");
    System.out.println("(Enter 2 for:) Normal Priority");
    System.out.println("(Enter 3 for:) Low Priority");
    System.out.print("\nPriority: ");
    priority = scanna.nextInt();
    scanna.nextLine();
    if(priority == 2)
    {
      priority = 5;
    }
    else if(priority == 3)
    {
       priority = 9;
    }
    
   
    /*System.out.println("Please enter a description of the event");
    System.out.print("Event summary: ");
    summary = scanna.nextLine();*/
    
    System.out.println("Please enter the date of the event");
    System.out.print("(YYYYMMDD): ");
    date = scanna.nextLine();
    
    System.out.println("Please enter the START time of the event");
    System.out.print("(24h format, i.e. 1500): ");
    startt = scanna.nextInt();
    
    System.out.println("Please enter the END time of the event");
    System.out.print("(24h format, i.e. 1500): ");
    endt = scanna.nextInt();
    
    System.out.println("Is this event recurring? (y/n): ");
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
       
       System.out.println("Please enter the END date for the recurring event");
       System.out.print("(or enter 'n' to never end): ");
       recEnd = scanna.nextLine();
       rec =1;
    }
    System.out.println("\nHow would you like to classify this event?");
    System.out.println("(1)Public");
    System.out.println("(2)Private");
    System.out.println("(3)Confidential\n");
    System.out.print("Your Selection: ");
    clnum = scanna.nextInt();
    scanna.nextLine();
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
