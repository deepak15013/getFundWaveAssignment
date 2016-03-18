package in.deepaksood;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimePeriod {
	public static List<String> getTimePeriods(java.sql.Date fromDate, java.sql.Date toDate, int timePeriod) {
		
		List<String> output = new ArrayList<String>();
		
		//@REFERENCE
		//http://stackoverflow.com/questions/29583936/
		//get-day-month-year-from-java-sql-date
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		int fromDay = cal.get(Calendar.DATE);
		int fromMonth = cal.get(Calendar.MONTH)+1;	//because month are indexed from 0 in calendar
		int fromYear = cal.get(Calendar.YEAR);
		
		cal.setTime(toDate);
		int toDay = cal.get(Calendar.DATE);
		int toMonth = cal.get(Calendar.MONTH)+1;	//because month are indexed from 0 in calendar
		int toYear = cal.get(Calendar.YEAR);
		
		System.out.println("from: "+fromDay+" "+fromMonth+" "+fromYear);
		System.out.println("to: "+toDay+" "+toMonth+" "+toYear);
		
		if(fromYear > toYear) {
			System.out.println("fromYear cannot be greater then toYear");
			System.exit(0);
		}
		if(fromYear == toYear && fromMonth > toMonth) {
			System.out.println("fromMonth cannot be greater then toMonth");
			System.exit(0);
		}
		if(fromYear == toYear && fromMonth == toMonth && fromDay > toDay) {
			System.out.println("fromDay cannot be greater then toDay");
			System.exit(0);
		}
		
		if(timePeriod == 0) {
			//Day
			int originalToMonth = toMonth;
			int originalToDay = toDay;
			while(fromYear <= toYear) {
				if(fromYear < toYear) {
					toMonth = 12;
				}
				else {
					toMonth = originalToMonth;
				}
				while(fromMonth <= toMonth) {
					if(fromMonth < toMonth) {
						toDay = getNumOfDays(fromMonth, fromYear);
						if(toDay == 0) {
							System.out.println("Error");
							System.exit(0);
						}
					}
					else
						toDay = originalToDay;
					while(fromDay <= toDay) {
						String mon = new DateFormatSymbols().getMonths()[fromMonth-1];
						output.add(String.valueOf(fromDay)+" "+mon);
						fromDay++;
					}
					fromMonth++;
					fromDay = 1;
				}
				fromYear++;
				fromMonth = 1;
			}
		}
		else if(timePeriod == 1) {
			//Month
			int originalToMonth = toMonth;
			while(fromYear <= toYear) {
				if(fromYear < toYear) {
					toMonth = 12;
				}
				else {
					toMonth = originalToMonth;
				}
				while(fromMonth <= toMonth) {
					String mon = new DateFormatSymbols().getMonths()[fromMonth -1];
					mon = mon.concat(" "+String.valueOf(fromYear));
					output.add(mon);
					fromMonth++;
				}
				fromYear++;
				fromMonth = 1;
			}
		}
		else if(timePeriod == 2) {
			//Quarter
			DateFormatSymbols dFS = new DateFormatSymbols();
			int originalToMonth = toMonth;
			while(fromYear <= toYear) {
				if(fromYear < toYear) {
					toMonth = 12;
				}
				else {
					toMonth = originalToMonth;
				}
				int flag = 0;
				while(fromMonth <= toMonth && flag < 5) {
					while(fromMonth % 3 != 0 && fromMonth != toMonth) {
						fromMonth++;
					}
					if(fromMonth == toMonth) {
						if(fromMonth % 3 == 0) {
							//http://stackoverflow.com/questions/30421069/
							//make-program-that-prints-month-name-according-to-corresponding-number-shorter
							output.add(dFS.getMonths()[fromMonth-1]+fromYear+" ");
							fromMonth++;
						}
					}
					else {
						output.add(dFS.getMonths()[fromMonth-1]+fromYear+" ");
						fromMonth++;
					}
					flag++;
				}
				fromYear++;
				fromMonth = 1;
			}
		}
		else if(timePeriod == 3) {
			//Year
			while(fromYear <= toYear) {
				output.add(String.valueOf(fromYear));
				fromYear++;
			}
		}
		return output;
		
	}
	
	public static int getNumOfDays(int toMonth, int fromYear) {
		//@REFERENCE
		//http://stackoverflow.com/
		//questions/16706716/using-two-values-for-one-switch-case-statement
		switch (toMonth) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
			
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
			
		case 2:
			if (((fromYear % 4 == 0) && 
                    !(fromYear % 100 == 0))
                    || (fromYear % 400 == 0))
                   return 29;
               else
                   return 28;
		default:
			return 0;				
		}		
	}
}
