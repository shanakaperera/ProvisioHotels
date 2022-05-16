package com.provisio.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.provisio.daos.HotelRoomDAO;
import com.provisio.models.Holiday;
import com.provisio.models.HotelRoom;

public class HolidayRatesUtil {

	public static BigDecimal addHolidayRates(String bookingDates, BigDecimal roomPrice) {

		BigDecimal rates = new BigDecimal(0);

		String[] dates = bookingDates.split("to");

		try {
			LocalDate checkin = LocalDate.parse(dates[0].trim(), DateFormatUtil.getFormatter());
			LocalDate checkout = LocalDate.parse(dates[1].trim(), DateFormatUtil.getFormatter());

			List<LocalDate> totalDates = new ArrayList<>();

			while (!checkin.isAfter(checkout)) {

				totalDates.add(checkin);
				checkin = checkin.plusDays(1);
			}

			DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd");

			for (LocalDate date : totalDates) {

				for (Holiday holiday : Holiday.values()) {

					if (holiday.getDate().equals(date.format(df)) && !checkout.format(df).equals(holiday.getDate())) {
						rates = rates.add(roomPrice.multiply(BigDecimal.valueOf(holiday.getIncrementRate())));
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rates;
	}

	public static Map<BigDecimal, Integer> fetchRoomRatesForDates(int hotelId, int hotelRoomId, String checkIn, String checkOut) {

		Map<BigDecimal, Integer> hotelNights = new HashMap<>();
		
		try {
			LocalDate checkin = LocalDate.parse(checkIn, DateFormatUtil.getFormatter());
			LocalDate checkout = LocalDate.parse(checkOut, DateFormatUtil.getFormatter());

			HotelRoomDAO hrdao = new HotelRoomDAO();
			HotelRoom hrm = hrdao.fetchHotelRoomById(hotelRoomId);

			List<LocalDate> totalDates = new ArrayList<>();

			while (!checkin.isAfter(checkout)) {

				totalDates.add(checkin);
				checkin = checkin.plusDays(1);
			}

			DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd");

			Map<String, BigDecimal> map = new HashMap<>();

			for (LocalDate date : totalDates) {

				map.put(date.format(df), hrm.getPrice());

				for (Holiday holiday : Holiday.values()) {

					if (holiday.getDate().equals(date.format(df)) && !checkout.format(df).equals(holiday.getDate())) {

						BigDecimal roomPrice = map.get(date.format(df))
								.add(map.get(date.format(df)).multiply(BigDecimal.valueOf(holiday.getIncrementRate())));

						map.put(date.format(df), roomPrice.setScale(2, RoundingMode.HALF_UP));
					}

				}
			}

			
			for (Map.Entry<String, BigDecimal> entry : map.entrySet()) {
				
				if(totalDates.size() == 1) {
					hotelNights.put(entry.getValue(), 1);
					break;
				}

				if (!checkout.format(df).equals(entry.getKey())) {
					hotelNights.put(entry.getValue(),
							hotelNights.containsKey(entry.getValue()) ? hotelNights.get(entry.getValue()) + 1
									: 1);
				}

			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hotelNights;
	}

}
