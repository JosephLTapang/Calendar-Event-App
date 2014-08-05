

import static org.junit.Assert.*;

import org.junit.Test;

public class icalendarTest {

	//Check if valid 24 hour format times return true with checkValidTime() method.
	@Test
	public void testValidTime(){
		assertTrue(yzcal.checkValidTime("1600"));
	}
	
	//Check if invalid 24 hour format times greater than 2400 return false with checkValidTime() method.
	@Test
	public void testGreaterInvalidTime(){
		assertFalse(yzcal.checkValidTime("160001"));
	}
	
	//Check if invalid 24 hour format times less than 2400 return false with checkValidTime() method.
	@Test
	public void testLowerInvalidTime(){
		assertFalse(yzcal.checkValidTime("111"));
	}
	
	//Check if a start time before an end time returns true with checkValidEndTime() method.
	@Test
	public void testStartTimeBeforeEndTime(){
		assertTrue(yzcal.checkValidEndTime("1200", "1500"));
	}
	
	//Check if a start time after an end time returns false with checkValidEndTime() method.
	@Test
	public void testEndTimeBeforeStartTime(){
		assertFalse(yzcal.checkValidEndTime("1800", "1500"));
	}
	
	//Check if date follows correct format YYYYMMDD with checkValidDate() method.
	@Test
	public void testValidDateFormat(){
		assertTrue(yzcal.checkValidDate("20140501"));
	}
	
	//Check if date follows correct format YYYYMMDD with checkValidDate() method.
	@Test
	public void testInvalidDateFormat(){
		assertFalse(yzcal.checkValidDate("29wx345222"));
	}
}
