package com.provisio.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.format.DateTimeFormatter;

// Date formatter
public class DateFormatUtil {

	private static final String DATE_FORMAT = "MM-dd-yyyy";

	public static String format(Date date) {

		if (date != null) {
			return new SimpleDateFormat(DATE_FORMAT).format(date);
		}

		return "";
	}

	public static String format(Date date, String format) {

		if (date != null) {
			return new SimpleDateFormat(format).format(date);
		}

		return "";
	}

	public static DateTimeFormatter getFormatter() {

		return DateTimeFormatter.ofPattern(DATE_FORMAT);
	}
}
