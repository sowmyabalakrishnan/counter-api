package com.counter.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.Map.Entry;
public class CounterUtil {
	
	public static int countTextOccurenceInPara(String para, String text) {		
		int count = 0, position = 0, index;
		if (para == null || text == null || para.length() == 0 || text.length() == 0) {
			return 0;
		}				
		para = para.toLowerCase();
		text = text.toLowerCase();
		while ((index = para.indexOf(text, position)) != -1) {
			++count;
			position = index + text.length();
		}				
		return count;		
	}
	
	public static List<Entry<String, Integer>> sortByCount(Map<String, Integer> textCountMap){		
		Set<Entry<String, Integer>> entrySet = textCountMap.entrySet();
		List<Entry<String, Integer>> entryList = new ArrayList<Entry<String, Integer>>(entrySet);
		Collections.sort( entryList, new Comparator<Map.Entry<String, Integer>>()
				{
			public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
			{
				return (o2.getValue()).compareTo( o1.getValue());
			}
				} );
		return entryList;
	}
	
	@SuppressWarnings("null")
	public static StringBuilder getInputSerchText(InputStream textInputStream) throws IOException {		
		BufferedReader bufferedReader = null;		
		StringBuilder sb = null;
		try
		{
			if(textInputStream!=null)
			{
				
				bufferedReader = new BufferedReader(new InputStreamReader(textInputStream));
				
				char[] charBuffer = new char[128];
		        int bytesRead = -1;
		       while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
		        	
		            sb.append(charBuffer, 0, bytesRead);
		        }
			}else {
		    	
		        sb.append("");
		    }
		} catch (IOException ex) {
			
		    throw ex;
		} finally {
			
		    if (bufferedReader != null) {
		        try {
		            bufferedReader.close();
		        } catch (IOException ex) {
		        	throw ex;
		        }
		    }
		}
		
		return sb;
	
	}

}
