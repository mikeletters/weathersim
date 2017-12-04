package com.cba.weather.sim.rules;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cba.weather.sim.model.Location;

public class Test {
	
	@org.junit.Test
	public void test() throws Exception {
		BigDecimal bd = new BigDecimal("0.55");
		bd = bd.multiply(new BigDecimal("0.9")).setScale(2, RoundingMode.HALF_UP);
		System.out.println(bd);
//		final int dec = (75 / 10) * 10;
//		System.out.println(dec);
//		System.out.println(Location.fromString("Osaka").getZone().getPressure(new Date()));
//		System.out.println(Location.fromString("Osaka").getZone().getPressure(new Date()));
//		final Date dateUtc = new SimpleDateFormat("Z").parse("-0100");
//		System.out.println(dateUtc.getTime());
//		System.out.println(Location.fromString("Osaka").getZone().getTemperature(new Date()).getTo());
//		System.out.println(Location.fromString("Osaka").getPosition());
	}

}
