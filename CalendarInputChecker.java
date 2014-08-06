import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class CalendarInputChecker {
	public static void main(String[] args){//Testing checkValidTime
		String date, startt, endt;
		date = "20120229";
		startt = "0000";
		endt = "1000";
		
		if(checkValidTime(date,startt,endt)){
			System.out.println("Correct");
		}
		else{
			System.out.println("Incorrect");
		}
		

		date = "20140229";
		startt = "0000";
		endt = "1000";
		
		if(checkValidTime(date,startt,endt)){
			System.out.println("Correct");
		}
		else{
			System.out.println("Incorrect");
		}
		
		date = "20140929";
		startt = "0000";
		endt = "1000";
		
		if(checkValidTime(date,startt,endt)){
			System.out.println("Correct");
		}
		else{
			System.out.println("Incorrect");
		}
	}
	public static boolean checkValidTime(String date, String startt, String endt){
    	if(!(checkValidDate(date))){
    		return false;
    	}
    	if(!(checkValidTime(startt))){
    		return false;
    	}
    	if(!(checkValidEndTime(startt, endt))){
    		return false;
    	}
    	return true;
    }
	public static boolean checkValidDate(String date){//In form YYYYMMDD
    	String formattedDate;
    	Date current = new Date();
    	
    	if(!date.matches("[0-9]+") || date.length() != 8)
        {
                return false;
        }
    	
		formattedDate = date.substring(4, 6) + "/" + date.substring(6, 8) + "/" + date.substring(0, 4);// MM/DD/YYYY
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date testDate = null;
		
		try{
			testDate = sdf.parse(formattedDate);
		}
		catch(ParseException pe){
			System.out.println("The entered date was invalid.");
			return false;
		}
		System.out.println("sdf: " + sdf.format(testDate));
		System.out.println("formattedDate: " + formattedDate);
		/*If the sdf.format(testDate) returns a different String than formattedDate string, the formatted date put into
		 * the testDate is incorrect in some way. Ex 12/32/2014 becomes 1/1/2015*/
		if(!(sdf.format(testDate).equals(formattedDate))){
			System.out.println("The entered date was invalid.");
			return false;
		}
		if(current.after(testDate)){
			System.out.println("The entered date is older than the current date.");
			return false;
		}
		return true;
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
}
