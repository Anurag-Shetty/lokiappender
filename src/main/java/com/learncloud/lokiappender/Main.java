package com.learncloud.lokiappender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	// Define a logger for the Main class
	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		// Example log messages at different levels
		/*
		 * logger.trace("This is a TRACE message.");
		 * logger.debug("This is a DEBUG message.");
		 * logger.info("This is an INFO message.");
		 */
		System.out.println("here in main");
		logger.warn("code new This is a WARN message.");
		logger.error("code new This is an ERROR message.");
		logger.fatal("code new This is a FATAL message.");
		// Just waiting loki to exit.
		try {
			System.out.println("Before sleep");
			Thread.sleep(3000); // Sleep for 3 seconds (3000 milliseconds)
			System.out.println("After sleep");
		} catch (InterruptedException e) {
			// Handle the InterruptedException if necessary
			e.printStackTrace();
		}
	}
}