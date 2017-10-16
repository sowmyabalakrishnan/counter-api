package com.counter.service;

import java.io.IOException;
import java.io.InputStream;

import org.json.simple.parser.ParseException;

public interface CounterService {

	public String countText(InputStream paraInputStream, InputStream textInputStream) throws IOException, ParseException;
	public String getHighestCount(InputStream paraInputStream, int returnLimit);
}
