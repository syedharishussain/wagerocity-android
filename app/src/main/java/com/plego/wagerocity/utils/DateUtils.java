package com.plego.wagerocity.utils;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hassan Jawed on 11/3/2015.
 */
public class DateUtils {

	public String getFormattedDateString (String dateString) throws ParseException {
		String strCurrentDate = "2015-05-06 18:05:00";
		Date newDate; // format.parse(strCurrentDate);
		newDate = convertDate( dateString );
		SimpleDateFormat format;
		//        Thursday, May 07, 2015
		format = new SimpleDateFormat( "EEEE, MMM dd, yyyy hh:mm" );
		String date = format.format( newDate );

		date = date + " CST";

		return date;
	}

	@NonNull public Date convertDate (String cstTime) throws ParseException {
		Date newDate;
		SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );//    "EEE, dd MMM yyyy hh:mm:ss Z");
		newDate = format.parse( cstTime );
		newDate.setTime( newDate.getTime() );//- 7200000
		return newDate;
	}

	public boolean isPastDate (String cstTime) throws ParseException {
		Calendar cstConvertedTime = Calendar.getInstance();
		cstConvertedTime.setTime( convertDate( cstTime ) );
		Calendar currentTime = Calendar.getInstance();
		return cstConvertedTime.after( currentTime );
	}
}
