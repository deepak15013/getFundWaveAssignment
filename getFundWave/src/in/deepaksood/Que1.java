package in.deepaksood;

import java.sql.Date;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Que1 {
	public static String output="";
	public static void main(String[] args) {
		
		String start = "2013-05-01";
		String end = "2014-09-02";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		java.util.Date startUtilDate = null;
		java.util.Date endUtilDate = null;
		try {
			startUtilDate = sdf.parse(start);
			endUtilDate = sdf.parse(end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//@REFERENCE
		//http://stackoverflow.com/questions/10413350/
		//date-conversion-from-string-to-sql-date-in-java-giving-different-output
		
		java.sql.Date sqlStartDate = new java.sql.Date(startUtilDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endUtilDate.getTime());
		
		int ret = printQuarters(sqlStartDate, sqlEndDate);
		if(ret == 0) {
			System.out.println("Error! StartDate should be smaller than EndDate");
		}
		else
			System.out.println(output);
	}
	
	public static int printQuarters(Date startDate, Date endDate) {

		//@REFERENCE
		//http://stackoverflow.com/questions/29583936/
		//get-day-month-year-from-java-sql-date
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int startMonth = cal.get(Calendar.MONTH)+1;	//because month are indexed from 0 in calendar
		int startYear = cal.get(Calendar.YEAR);
		
		cal.setTime(endDate);
		int endMonth = cal.get(Calendar.MONTH)+1;	//because month are indexed from 0 in calendar
		int endYear = cal.get(Calendar.YEAR);
		
		DateFormatSymbols dFS = new DateFormatSymbols();
		
		if(startYear > endYear) {
			return 0;
		}
		if(startYear == endYear) {
			if(startMonth > endMonth) {
				return 0;
			}
		}
		
		int originalendMonth = endMonth;
		while(startYear <= endYear) {
			if(startYear < endYear) {
				endMonth = 12;
			}
			else {
				endMonth = originalendMonth;
			}
			int flag = 0;
			while(startMonth <= endMonth && flag < 5) {
				while(startMonth % 3 != 0 && startMonth != endMonth) {
					startMonth++;
				}
				if(startMonth == endMonth) {
					if(startMonth % 3 == 0) {
						//http://stackoverflow.com/questions/30421069/
						//make-program-that-prints-month-name-according-to-corresponding-number-shorter
						output = output.concat(dFS.getMonths()[startMonth-1]+startYear+" ");
						startMonth++;
					}
				}
				else {
					output = output.concat(dFS.getMonths()[startMonth-1]+startYear+" ");
					startMonth++;
				}
				flag++;
			}
			startYear++;
			startMonth = 1;
		}
		return 1;
	}
}
