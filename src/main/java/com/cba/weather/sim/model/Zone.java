package com.cba.weather.sim.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cba.weather.sim.common.Utils;

public class Zone {
	private final List<CSVRecord> lines;
	private Zone(String res) throws IOException {
		lines = Utils.getParser(res).getRecords();
	}
	
	private Range<BigDecimal> getField(Date date, boolean sHemi, String field) throws IOException {
		final String [] range = lines.get(getMonth(date, sHemi)).get(field).split(":");
		return new Range<BigDecimal>(new BigDecimal(range[0]), new BigDecimal(range[1]));
	}
	
	private static int getMonth(Date date, boolean sHemi) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (sHemi){
			cal.add(Calendar.MONTH, 6);
		}
		return cal.get(Calendar.MONTH);
	}
	
	public Range<BigDecimal> getTemperature(Date date, boolean sHemi) throws IOException {
		return getField(date, sHemi, "Temperature");
	}
	
	public Range<BigDecimal> getPressure(Date date, boolean sHemi) throws IOException {
		return getField(date, sHemi, "Pressure");
	}
	
	public Range<BigDecimal> getHumidity(Date date, boolean sHemi) throws IOException {
		return getField(date, sHemi, "Humidity");
	}
	
	public static Zone fromString(String name) throws IOException {
		return new Zone("zones/"+ name + ".txt");
	}
	
}
