package com.cba.weather.sim.rules;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.IntStream;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Assert;
import org.junit.Test;

import com.cba.weather.sim.common.Utils;
import com.cba.weather.sim.model.Condition;
import com.cba.weather.sim.model.Location;
import com.cba.weather.sim.model.Range;
import com.cba.weather.sim.model.Weather;

public class RulesTest {
	
	@Test
	public void testWeather() throws Exception {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		final CSVParser csv = Utils.getParser("samples.txt");
		for (CSVRecord record : csv) {
			final Date date = sdf.parse(record.get("TimeUTC"));
			final String locName = record.get("Location");
			final Location loc = Location.fromString(locName);
			final Weather actual = Rules.process(loc, date, null);
			final Weather expected = new Weather(loc, date, Condition.SUNNY, record.get("Temperature"), record.get("Pressure"), record.get("Humidity"));
			checkWeather(expected, actual);
		}
	}
	
	private static void checkWeather(Weather expected, Weather actual) {
		Utils.out(expected);
		Utils.out(actual);
		System.out.println();
		if (expected.getHumidity().compareTo(new BigDecimal("50")) > 0){
			checkBound(expected.getTemperature(), actual.getTemperature(), new BigDecimal(10));
		}
	}

	private static void checkBound(BigDecimal expected, BigDecimal actual, BigDecimal bound) {
		Assert.assertTrue(expected.subtract(actual).abs().compareTo(bound) < 0);
	}
	
	
	@Test
	public void testRand() {
		IntStream.range(0, 100).forEach((it) -> {
			final BigDecimal val = Rules.rand(new Range<BigDecimal>(new BigDecimal("0"), new BigDecimal("100")), it);
			Assert.assertEquals(it, val.intValue());
		});
	}
}
