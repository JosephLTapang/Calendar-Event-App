import java.io.*;

public class EventGeneratorMain {

public static void main(String[] args) {

//Strings representing each text field in the .ics file.
String text = "BEGIN:VCALENDAR";   //BEGIN's and END's allows calendar program to where to start and end.
String text2 = "VERSION:2.0";
String text3 = "X-WR-TIMEZONE:Pacific/Honolulu";
String text4 = "BEGIN:VEVENT";
String text5 = "DTSTART:20140813T200000Z";  //10 am start
String text6 = "DTEND: 20140813T230000Z";    //1 pm end
String text7 = "DTSTAMP: 20140722T27310Z"; //When the event was created.
String text8 = "DESCRITPION: Review old quizzes and lecture notes.";
String text9 = "LOCATION: Hamilton Library";
String text10 = "SUMMARY: Study for Exam";
String text11 = "END:VEVENT";
String text12 = "END:VCALENDAR";
                                                                      
try{
                        //creates a new .ics file
File file = new File("newCalendarFile.ics"); 
BufferedWriter output = new BufferedWriter(new FileWriter(file));
                        //writes the strings to the file where each string is followed by a new line.
output.write(text + "\r\n");
output.write(text2 + "\r\n");
output.write(text3 + "\r\n");
output.write(text4 + "\r\n");
output.write(text5 + "\r\n");
output.write(text6 + "\r\n");
output.write(text7 + "\r\n");
output.write(text8 + "\r\n");
output.write(text9 + "\r\n");
output.write(text10 + "\r\n");
output.write(text11 + "\r\n");
output.write(text12 + "\r\n");
output.close();
                //A basic io exception for now
}catch(IOException e) 
{
e.printStackTrace();
}
                //Confirmation to at least let you know the program executed.
System.out.print("File Generated.");
}
}