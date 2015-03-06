/*
 * 
 */
package net.petercashel.commonlib.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateConverters {

	/**
	 * Gets the month name.
	 *
	 * @param i the i
	 * @return the month name
	 */
	public static String getMonthName(int i) {
		switch(i) {
		default: { return String.valueOf(i);}
		case 1: { return "January";}
		case 2: { return "Feburary";}
		case 3: { return "March";}
		case 4: { return "April";}
		case 5: { return "May";}
		case 6: { return "June";}
		case 7: { return "July";}
		case 8: { return "August";}
		case 9: { return "September";}
		case 10: { return "October";}
		case 11: { return "November";}
		case 12: { return "December";}
		}
	}

	/**
	 * Gets the month int.
	 *
	 * @param text the text
	 * @return the month int
	 */
	public static int getMonthInt(String text) {

		try {
			Date date = new SimpleDateFormat("MMM", Locale.getDefault()).parse(text);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return (cal.get(Calendar.MONTH) + 1);

		} catch (Exception e){
			e.printStackTrace();
			//It's preferred that this isn't ever needed,
			//returns -1 so the sanity checks can throw error popups
			return -1;
		}
	}


}
