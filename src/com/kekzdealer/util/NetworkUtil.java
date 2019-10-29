package com.kekzdealer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetworkUtil {
	
	public static String readStreamToEnd(InputStream is) {
		final StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))){
			String line;
			while((line = br.readLine()) != null) {
				sb.append(line);
			}			
		} catch (IOException e) {
			Logger.ERROR.print("Error reading from an input stream");
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
}
