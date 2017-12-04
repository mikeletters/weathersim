package com.cba.weather.sim.model;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.cba.weather.sim.common.Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Location {
	private final String name;
	private final String position;
	private final Zone zone;
	private final long timezone;
	private static final Map<String, Location> LOC = Maps.newLinkedHashMap();
	
	static {
    	try {
    		final CSVParser csv = Utils.getParser("locations.txt");
    		for (CSVRecord record : csv) {
    			final String name = record.get("Location");
    			final String position = record.get("Position");
    			final Zone zone = Zone.fromString(record.get("Zone"));
    			final long timezone = -new SimpleDateFormat("Z").parse(record.get("Timezone")).getTime();
    			final Location l = new Location(name, position, zone, timezone);
    			LOC.put(name, l);
    		}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Location fromString(String name) {
		return LOC.get(name);
	}
	
	public static List<Location> locations() {
		return Lists.newArrayList(LOC.values());
	}

	public Location(String name, String position, Zone zone, long timezone) {
		this.name = name;
		this.position = position;
		this.zone = zone;
		this.timezone = timezone;
	}

	public String getName() {
		return name;
	}

	public String getPosition() {
		return position;
	}

	public Zone getZone() {
		return zone;
	}

	public long getTimezone() {
		return timezone;
	}
	
	public boolean isSHemi() {
		return position.startsWith("-");
	}

}
