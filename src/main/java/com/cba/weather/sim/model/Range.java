package com.cba.weather.sim.model;

public class Range<T extends Comparable<T>> {
	private final T from;
	private final T to;
	public Range(T from, T to) {
		this.from = from;
		this.to = to;
	}
	public Range(T fromTo) {
		this.from = fromTo;
		this.to = fromTo;
	}
	public T getFrom() {
		return from;
	}
	public T getTo() {
		return to;
	}
}
