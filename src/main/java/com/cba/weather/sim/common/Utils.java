package com.cba.weather.sim.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import com.cba.weather.sim.model.Location;
import com.cba.weather.sim.model.Weather;
import com.google.common.io.Resources;

public final class Utils {
	
	public static CSVParser getParser(String res) throws FileNotFoundException, IOException {
		return CSVFormat
				.DEFAULT
				.withHeader()
				.parse(new FileReader(Resources.getResource(res).getFile()));
	}
	
    public static void out(List<Weather> w) {
    	w.forEach((it) -> {out(it);});
    	System.out.println();
    }
    
    public static void out(Weather w) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		final Location loc = w.getLocation();
		TimeZone tz = TimeZone.getTimeZone("UTC");
		tz.setRawOffset((int)loc.getTimezone());
		sdf.setTimeZone(tz);
    	final String [] elements = new String [] {
    			loc.getName(),
    			loc.getPosition().replace(":", ","),
    			sdf.format(new Date(w.getDate().getTime())),
    			w.getCondition().toString(),
    			w.getTemperature().toString(),
    			w.getPressure().toString(),
    			w.getHumidity().toString()
    	};
		System.out.println(String.join("|", elements));
    }
}

