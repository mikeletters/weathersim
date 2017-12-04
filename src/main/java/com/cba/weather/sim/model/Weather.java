package com.cba.weather.sim.model;

import java.math.BigDecimal;
import java.util.Date;

public class Weather {
	
	private final Location location;
	private final Date date;
	private final Condition condition;
	private final BigDecimal temperature;
	private final BigDecimal pressure;
	private final BigDecimal humidity;
	public Weather(Location location, Date date, Condition condition, String temperature, String pressure,
			String humidity) {
		this.location = location;
		this.date = date;
		this.condition = condition;
		this.temperature = new BigDecimal(temperature);
		this.pressure = new BigDecimal(pressure);
		this.humidity = new BigDecimal(humidity);
	}
	public Weather(Location location, Date date, Condition condition, BigDecimal temperature, BigDecimal pressure,
			BigDecimal humidity) {
		this.location = location;
		this.date = date;
		this.condition = condition;
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
	}
	public Location getLocation() {
		return location;
	}
	public Condition getCondition() {
		return condition;
	}
	public BigDecimal getTemperature() {
		return temperature;
	}
	public BigDecimal getPressure() {
		return pressure;
	}
	public BigDecimal getHumidity() {
		return humidity;
	}
	public Date getDate() {
		return date;
	}
}
