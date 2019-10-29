package com.kekzdealer.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
	private static final Logger logger = new Logger();
	public static final InfoLogger INFO = logger.new InfoLogger();
	public static final ErrorLogger ERROR = logger.new ErrorLogger();
	
	protected static String getTimestamp() {
		return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	}
	
	public class InfoLogger extends Logger {
		
		/**
		 * Prints to the standard output stream with a timestamp
		 * 
		 * @param 
		 * 			Message to print
		 */
		public void print(String s) {
			System.out.print(Logger.getTimestamp() + "/INFO: " + s);
		}
		
		public void log(String s) {
			// write to log file
		}
	}
	
	public class ErrorLogger extends Logger {
		
		/**
		 * Prints to the standard error stream with a timestamp
		 * 
		 * @param 
		 * 			Message to print
		 */
		public void print(String s) {
			System.err.println(Logger.getTimestamp() + "/ERROR: " + s);
		}
		
		public void log(String s) {
			// write to log file
		}
	}
}
