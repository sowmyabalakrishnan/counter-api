package com.counter.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.counter.utils.Constants;
import com.counter.utils.CounterUtil;

@Service
public class CounterServiceImpl implements CounterService{
	
	@SuppressWarnings("unchecked")
	public String countText(InputStream paraInputStream, InputStream textInputStream) throws IOException, ParseException {
		String paraString = "", returnText = "";
		JSONParser parser = new JSONParser();
		JSONObject json = new JSONObject();
		Object obj = null;
		HashMap<String, Integer> textCountMap = new HashMap<String, Integer>();	
		try {
			paraString = IOUtils.toString(paraInputStream, Constants.UTF_8);
			obj = parser.parse(IOUtils.toString(textInputStream, Constants.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
			return Constants.COUNTERSERVICE_ERR_MSG_1;
		}
	
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray inputTextJsonArr = (JSONArray) jsonObject.get(Constants.SEARCH_TEXT);
		for(Object text : inputTextJsonArr){
			int count = CounterUtil.countTextOccurenceInPara(paraString, text.toString());
			returnText = text.toString().substring(0, 1).toUpperCase() + text.toString().substring(1).toLowerCase();
			textCountMap.put(returnText, count);
		}
		json.put(Constants.COUNTS, textCountMap);
		return json.toString();
	}

	
	@Override
	public String getHighestCount(InputStream paraInputStream, int returnLimit) {
		StringBuilder returnStringBuilder = new StringBuilder();
		Map<String, Integer> textCountMap = new HashMap<String, Integer>();

		String paraString = null;
		int value =0;
		try {
			paraString = IOUtils.toString(paraInputStream, Constants.UTF_8).toLowerCase().replaceAll("[,.;]", "");
			paraString = paraString.replace(System.getProperty("line.separator"), " ");
		} catch (IOException e) {
			e.printStackTrace();
			return Constants.COUNTERSERVICE_ERR_MSG_2;
		}
		
		String textArr[] = paraString.split(" ");		
		for(String text : textArr)
			if(textCountMap.containsKey(text.trim())){
				textCountMap.put(text, textCountMap.get(text.trim())+1);
			} else {
				textCountMap.put(text.trim(), 1);
			}

		List<Entry<String, Integer>> list = CounterUtil.sortByCount(textCountMap);
		for(Map.Entry<String, Integer> entry:list){
			++value;
			if(value<=returnLimit){
				returnStringBuilder.append(entry.getKey()+Constants.PIPE_SYMBOL+entry.getValue());
				returnStringBuilder.append(System.getProperty("line.separator"));
			}
		}		
		return returnStringBuilder.toString();
	}

	
}
