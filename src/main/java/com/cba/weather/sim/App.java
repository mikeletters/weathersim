package com.cba.weather.sim;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.cba.weather.sim.common.Utils;
import com.cba.weather.sim.model.Location;
import com.cba.weather.sim.model.Weather;
import com.cba.weather.sim.rules.Rules;
import com.google.common.collect.Lists;

public class App {
	//TODO: location as parameter, get zone from Köppen map and height from height map
    public static void main( String[] args ) throws Exception {
    	if (args.length < 2) {
    		System.out.println("Arguments: date(yyyyMMddHHmmss) mode([continuous|oneoff]) ");
    		return;
    	}
        new App().start(args[1], parseDate(args[0]));
    }
    
    public void start(String mode, Date dateUtc) throws Exception {
    	final List<Weather> weather = init(Location.locations(), dateUtc);
    	Utils.out(weather);
    	if (mode.equals("oneoff")) {
    		return;
    	}
    	final long offset = 3600000L*720;
    	List<Weather> currentWeather = weather;
    	while (true) {
    		currentWeather = progress(currentWeather, offset);
    		Utils.out(currentWeather);
    		Thread.sleep(3000);
    	}
    }
    
    private static Date parseDate(String str) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.parse(str);
    }

    private static List<Weather> init(List<Location> locations, Date date) throws IOException {
    	final List<Weather> res = Lists.newArrayList();
    	for (Location loc : locations){
    		res.add(Rules.process(loc, date, null));
    	}
    	return res;
    }
    
    private List<Weather> progress(List<Weather> weather, long offset) throws IOException {
    	final List<Weather> res = Lists.newArrayList();
    	for (Weather w : weather) {
    		res.add(Rules.process(w.getLocation(), new Date(w.getDate().getTime() + offset), w));
    	}
    	return res;
    }
    
}
