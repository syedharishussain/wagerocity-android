package com.plego.wagerocity.utils;

import android.test.InstrumentationTestCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Hassan on 9/16/2015.
 */
public class UtilsTest extends InstrumentationTestCase {

	public void testDateConversion () throws ParseException {
		String cstDateString = "2015-09-15 12:35:00";
		SimpleDateFormat cstFormatter = new SimpleDateFormat( "yyyy-MM-dd kk:mm:ss" );
		Date cstDate = cstFormatter.parse( cstDateString );
		Calendar instance = Calendar.getInstance();
		instance.setTime( cstDate );
		instance.add( Calendar.HOUR_OF_DAY, 5 );
		String calendarTime = cstFormatter.format( instance.getTime() );
		assertEquals( calendarTime, "2015-09-15 17:35:00" );

		int offset = TimeZone.getDefault().getOffset( instance.getTimeInMillis() );
		instance.setTimeInMillis( instance.getTimeInMillis() + offset );

		calendarTime = cstFormatter.format( instance.getTime() );

		assertEquals( calendarTime, "2015-09-15 22:35:00" );
	}

	public void testPastTime () throws ParseException {
		// cst time to UTC
		String cstDateString = "2015-09-15 12:35:00";
		SimpleDateFormat cstFormatter = new SimpleDateFormat( "yyyy-MM-dd kk:mm:ss" );
		Date cstDate = cstFormatter.parse( cstDateString );
		Calendar cstUtcTime = Calendar.getInstance();
		cstUtcTime.setTime( cstDate );
		cstUtcTime.add( Calendar.HOUR_OF_DAY, 6 );

		// Current time to UTC
		Calendar instance = Calendar.getInstance();
		int offset = TimeZone.getDefault().getOffset( instance.getTimeInMillis() );
		instance.setTimeInMillis( instance.getTimeInMillis() - offset );

		assertTrue( cstUtcTime.before( instance ) );

		cstDateString = "2015-10-04 00:45:00";
		cstDate = cstFormatter.parse( cstDateString );
		cstUtcTime.setTime( cstDate );
		cstUtcTime.add( Calendar.HOUR_OF_DAY, 6 );
		assertFalse( cstUtcTime.before( instance ) );

		Calendar currentTime = Calendar.getInstance();
		int cstOffset = TimeZone.getDefault().getOffset( instance.getTimeInMillis() ) - 6;
		currentTime.setTimeInMillis( currentTime.getTimeInMillis() + cstOffset );

		assertFalse( currentTime.before( instance ) );
	}
}
