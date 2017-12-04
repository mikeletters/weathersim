package com.cba.weather.sim.model;

public enum Condition {
	SNOW("Snow", true), RAIN("Rain", true), SUNNY("Sunny", false);
	private final String name;
	private boolean fallout;
	private Condition(String name, boolean fallout) {
		this.name= name;
		this.fallout= fallout;
	}
	@Override
	public String toString() {
		return name;
	}
	public boolean isFallout() {
		return fallout;
	}
	
}
