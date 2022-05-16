package com.provisio.utils;

import java.text.DecimalFormat;

// Currency formatter
public class CurrencyFormatUtil {

	public static String format(double value) {

		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(value);
	}
}
