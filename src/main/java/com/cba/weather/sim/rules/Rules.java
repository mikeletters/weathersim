package com.cba.weather.sim.rules;

import java.io.IOException;
import java.math.BigDecimal;

import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import com.cba.weather.sim.model.Condition;
import com.cba.weather.sim.model.Location;
import com.cba.weather.sim.model.Range;
import com.cba.weather.sim.model.Weather;
import com.cba.weather.sim.model.Zone;
import com.google.common.collect.Maps;

public class Rules {
	private Rules() {}
	
	static BigDecimal rand(Range<BigDecimal> r, Integer rand) {
		final BigDecimal offset = r.getTo().subtract(r.getFrom()).multiply(new BigDecimal(rand)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
		return r.getFrom().add(offset);
	}
	
	//TODO: night temperature offset
	//TODO: temperature and pressure offset from height
	public static Weather process(Location loc, Date date, Weather prevWeather) throws IOException {
		final Zone z = loc.getZone();
		final Random rand = new Random();
		final boolean sHemi = loc.isSHemi();
		final BigDecimal temp = rand(z.getTemperature(date, sHemi), rand.nextInt(100));
		final BigDecimal pres = rand(z.getPressure(date, sHemi), rand.nextInt(100));
		final BigDecimal hum = rand(z.getHumidity(date, sHemi), rand.nextInt(100));
		final Condition c = getCondition(temp, hum);
		return new Weather(loc, date, c, temp, pres, amendHumidity(hum, prevWeather));
	}
	
	private static BigDecimal amendHumidity(BigDecimal thisHum, Weather prevWeather) {
		if (prevWeather == null){
			return thisHum;
		}
		final boolean prevF = prevWeather.getCondition().isFallout();
		if (prevF) {
			return prevWeather.getHumidity().multiply(new BigDecimal("0.9")).setScale(2, RoundingMode.HALF_UP);
		}
		return thisHum;
	}
	
	private static Map<Integer, Integer> falloutProb = Maps.newHashMap();
	static {
		falloutProb.put(0, 0);
		falloutProb.put(10, 5);
		falloutProb.put(20, 7);
		falloutProb.put(30, 10);
		falloutProb.put(40, 15);
		falloutProb.put(50, 25);
		falloutProb.put(60, 37);
		falloutProb.put(70, 55);
		falloutProb.put(80, 67);
		falloutProb.put(90, 75);
		falloutProb.put(100, 80);
	}
	
	private static boolean isFallout(BigDecimal humidity) {
		final int rnd = new Random().nextInt(100);
		final int dec = (humidity.intValue() / 10) * 10;
		final int rem = humidity.intValue() % 10;
		final int probBase = falloutProb.get(dec);
		final int probNext = falloutProb.getOrDefault(dec + 10, probBase);
		final int prob = probBase + (((probNext - probBase) * rem) / 10);
		if (rnd <= prob) {
			return true;
		}
		return false;
	}
	
	private static Condition getCondition(BigDecimal temperature, BigDecimal humidity) {
		if (isFallout(humidity)) {
			if (temperature.signum() == -1) {
				return Condition.SNOW;
			} else {
				return Condition.RAIN;
			}
		}
		return Condition.SUNNY;
	}
	
}
