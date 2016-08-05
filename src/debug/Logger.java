package debug;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import display.GameDisplay;

/**
 * 
 * @author Joshua Kuennen
 * A class that logs what happens in the game into a text file
 */
public class Logger {

	/**
	 * The file into which the logs are inserted.
	 */
	public static File log = new File(System.getProperty("user.home") + "\\BS2014\\log.txt");
	
	/**
	 * The FileReader Object that is used to create the BufferedReader
	 */
	private static FileReader reader;
	
	/**
	 * The BufferedReader that reads the file. Used to keep past logs.
	 */
	private static BufferedReader bfReader;
	
	/**
	 * The StringBuilder to build the string and put then save as a file.
	 */
	private static StringBuilder builder;

	/**
	 * Dummy constructor
	 */
	public Logger() {

	}

	/**
	 * Opens the file, if it exists, otherwise creates a new file.
	 * Sets up the Reading and Building of the file.
	 * Makes sure to keep old logs.
	 */
	public static void init() {
		if (!log.exists()) {
			try {
				log.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			reader = new FileReader(log);
			bfReader = new BufferedReader(reader);
			builder = new StringBuilder();
			String line = null;
			int i = 0;
			while ((line = bfReader.readLine()) != null) {
				builder.append(line + ";");
				i++;
			}
			System.out.println(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a new log to the file.
	 * @param s the log to add to the file.
	 */
	public static void log(String s) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		Date date = new Date();
		String log = "[" + dateFormat.format(date) + "] " + s + ": v" + GameDisplay.VERSION +  ";";
		builder.append(log);
		System.out.println(log);
	}
	
	/**
	 * Writes the file when the game is closed. 
	 */
	public static void cleanup() {
		try {
			PrintWriter writer = new PrintWriter(log, "UTF-8");
			for (String s : builder.toString().split(";")) {
				writer.println(s);
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}
