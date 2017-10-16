package com.counter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.io.InputStream;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.counter.service.CounterService;
import com.counter.utils.Constants;

@RestController
public class CounterController {
	
	@Autowired
	private CounterService counterService;
	
	@RequestMapping("/")
	 public String welcome() {
	 return Constants.WELCOME_STRING;
	 }
		
	@RequestMapping(value = "/search", method = {RequestMethod.POST,RequestMethod.GET}, headers="Accept=application/json")
	public String getTextCountInPara() throws IOException, ParseException {		
		Resource paraResource = new ClassPathResource(Constants.SAMPLE_PARA_FILE);		
		Resource textResource = new ClassPathResource(Constants.SEARCH_TEXT_FILE);		
		InputStream paraInputStream = paraResource.getInputStream();
		InputStream textInputStream = textResource.getInputStream();		
		return counterService.countText(paraInputStream, textInputStream);	
		}
		
	@RequestMapping(value = "/top/{returnLimit}", headers="Accept=application/csv")
	public String  getTopTextInPara(@PathVariable int returnLimit) throws IOException{
		Resource paraResource = new ClassPathResource(Constants.SAMPLE_PARA_FILE);
		InputStream paraInputStream = paraResource.getInputStream();	
		return counterService.getHighestCount(paraInputStream, returnLimit);
		}

}
