package in.deepaksood;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Que2 {
	public static void main(String[] args) {
		String stringFromDate = "1 Dec 2014";
		String stringToDate = "20 Dec 2015";
		String stringTimePeriod = "TimePeriod.QUARTER";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		
		java.util.Date fromUtilDate = null;
		java.util.Date toUtilDate = null;
		
		try {
			fromUtilDate = sdf.parse(stringFromDate);
			toUtilDate = sdf.parse(stringToDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//@REFERENCE
		//http://stackoverflow.com/questions/10413350/
		//date-conversion-from-string-to-sql-date-in-java-giving-different-output
				
		java.sql.Date fromDate = new java.sql.Date(fromUtilDate.getTime());
		java.sql.Date toDate = new java.sql.Date(toUtilDate.getTime());
		
		//TimePeriod.DAY = 0
		//TimePeriod.MONTH = 1;
		//TimePeriod.QUARTER = 2;
		//TimePeriod.YEAR = 3;
		
		int timePeriod = getTimePeriod(stringTimePeriod);
		
		List<String> timePeriods = TimePeriod.getTimePeriods(fromDate, toDate, timePeriod);
		
		int i=0;
		for(i=0; i<timePeriods.size()-1;i++) {
			System.out.print(timePeriods.get(i)+", ");
		}
		if(i != 0)	//for corner case: no entry in list
			System.out.println(timePeriods.get(i));
	}

	private static int getTimePeriod(String stringTimePeriod) {
		// TODO Auto-generated method stub
		switch (stringTimePeriod) {
		case "TimePeriod.DAY":
			return 0;
		case "TimePeriod.MONTH":
			return 1;
		case "TimePeriod.QUARTER":
			return 2;
		case "TimePeriod.YEAR":
			return 3;
		default:
			return 0;	//if wrong input is given then byDefault numOfDays will be showed;
		}		
	}
}
