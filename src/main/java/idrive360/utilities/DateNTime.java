package idrive360.utilities;

import java.text.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateNTime {
	DateFormat dateFormat = null;
	Date currentDate = null;
	public String displayDateNTime = null;
	public static Logger log = LogManager.getLogger(DateNTime.class);

	public String printCurrentDateTime() {
		// get current date time with Date()
		currentDate = new Date();

		// Create object of SimpleDateFormat class and decide the format
		dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		// Now format the date
		displayDateNTime = dateFormat.format(currentDate);

		return displayDateNTime;
	}

	public String printCurrentTime() {
		// get current date time with Date()
		currentDate = new Date();

		// Create object of SimpleDateFormat class and decide the format
		dateFormat = new SimpleDateFormat("h:mm:ss");

		// Now format the date
		displayDateNTime = dateFormat.format(currentDate);

		return displayDateNTime;
	}

	public String printCurrentDate() {
		// get current date time with Date()
		currentDate = new Date();

		// Create object of SimpleDateFormat class and decide the format
		dateFormat = new SimpleDateFormat("dd/MM/yy");

		// Now format the date
		displayDateNTime = dateFormat.format(currentDate);

		return displayDateNTime;
	}

	// Form the email id with todays date and time to make it unique

	/**
	 * This method is used to create unique emails with the date & time
	 * 
	 * @param userCredentials this will take parameter as username with + with date
	 *                        & time to create email ids
	 * @return unique email id with date & time
	 */

	public String emailCreationWithDateNTime(String emailID) {
		String date = printCurrentDate();
		String time = printCurrentTime();
		date = date.replace("/", "");
		time = (time.replace(":", "")).replace(" ", "").toLowerCase();
		if (!emailID.contains(date)) {
			int index = emailID.indexOf('+');
			emailID = emailID.substring(0, index + 1) + date + "_" + time + emailID.substring(index + 1);
		}
		log.info("Email created from dateNTimeutility :" + emailID);
		return emailID;
	}

	/**
	 * This method is used to create unique emails with the date
	 * 
	 * @param userCredentials this will take parameter as username with + with date
	 *                        to create email ids
	 * @return unique email id with date
	 */
	public String emailCreationWithDate(String emailID) {
		// Form the email id with todays date to make it unique
		String date = printCurrentDate();
		date = date.replace("/", "");
		if (!emailID.contains(date)) {
			int index = emailID.indexOf('+');
			emailID = emailID.substring(0, index + 1) + date + emailID.substring(index + 1);
		}
		log.info("Email created from dateNTimeutility : " + emailID);
		return emailID;
	}

}
