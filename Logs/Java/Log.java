package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Manages all system logs.
 * Separates them for easy visibility.
 * @author Sameed Ali
 */
public class Log {

	private static boolean diskWrite;         // writes to disk if true;
	private String LOGTAG;                    // logtag
	private String methodTag;                 // method tag fir easier debugging
	private static LogState logState;         // log state
	private static LogLevel logLevel;         // loglevel
	private static boolean printRequiredTags; // print tags for required fields

	/**
	 * Defines State to log files.
	 * @author Sameed Ali
	 */
	public enum LogState
	{
		DISK_ONLY,
		DISK_AND_CONSOLE,
		CONSOLE_ONLY,
	}

	/**
	 * Defines logging level
	 * @author Sameed Ali
	 *
	 */
	public enum LogLevel
	{
		VERBOSE(1) ,
		DEBUG(2),
		WARNING(3),
		CRITICAL(4),
		REQUIRED(5),
		OFF(6);

		int value;

		//constructor
		LogLevel(int _value)
		{
			value = _value;
		}

		//get value
		int getValue()
		{
			return value;
		}
	}

	/**
	 * DiskLog prints output to the System along with the TAG.
	 */
	public Log(String _logTag)
	{
		LOGTAG = _logTag + " :: ";
		printRequiredTags = false;
		diskWrite = false;
	}

	/**
	 * Sets the log level for the program log.
	 */
	public static void setLogLevel(LogLevel _logLevel)
	{
		logLevel = _logLevel;
		logLevel.value = _logLevel.getValue();
	}

	/**
	 * updates the method tag of the system log.
	 * @param _methodTag
	 */
	public void setMethodTag(String _methodTag)
	{
		methodTag = _methodTag + " -> ";
	}

	/**
	 * Handles all verbose logs
	 */
	public void verbose(String _log)
	{
		if( logLevel.getValue() < (LogLevel.VERBOSE.getValue()+1) )
		{
			System.out.println("verbose :: " + LOGTAG + methodTag + _log);
		}
	}

	/**
	 * Handles all warning logs
	 * @param _log
	 */
	public void warning(String _log)
	{
		if( logLevel.getValue() < (LogLevel.WARNING.getValue()+1) )
		{
			System.out.println("WARNING :: " + LOGTAG + methodTag + _log);
		}
	}

	/**
	 * Handles debug logs
	 * @param _log
	 */
	public void debug(String _log)
	{
		if( logLevel.getValue() < (LogLevel.DEBUG.getValue()+1) )
		{
			System.out.println("debug   :: " + LOGTAG + methodTag + _log);
		}
	}

	/**
	 * Logs all critical logs
	 * @param _log
	 */
	public void critical(String _log)
	{
		if( logLevel.getValue() < (LogLevel.CRITICAL.getValue()+1) )
		{
			System.err.println("CRITICAL:: " + LOGTAG + methodTag + _log);
		}

	}

	/**
	 * Handles Critical exceptions
	 * @param exception
	 */
	public void critical(Exception exception)
	{
		if( logLevel.getValue() < (LogLevel.CRITICAL.getValue()+1) )
		{
			System.err.println("CRITICAL:: " + LOGTAG + methodTag + exception.getMessage());
			System.err.println("CRITICAL:: " + LOGTAG + methodTag + "Printing stack trace:");
			exception.printStackTrace();
		}
	}


	/**
	 * Handles all required logs
	 * @param _log
	 */
	public void required(String _log)
	{
		if( logLevel.getValue() < (LogLevel.REQUIRED.getValue()+1) )
		{
			if(!printRequiredTags)
			{
				System.err.println(_log);
			}
			else
			{
				System.err.println("REQUIRED:: " + LOGTAG + methodTag + _log);
			}
		}
	}

	/**
	 * sets weather to print required Tags.
	 * @param _printTags
	 */
	public void setPrintRequiredTags(boolean _printTags)
	{
		printRequiredTags = _printTags;
	}

	/**
	 * Write given log to disk
	 * @param _log
	 */
	public void diskWrite(String _log)
	{
		try
		{
			File fout = new File(getClass().getPackage().getName() +"_DiskLog.log");
			FileOutputStream fos = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write(LOGTAG + _log);
			bw.newLine();
			bw.close();
			return;
		}
		catch(FileNotFoundException fileNotFoundException)
		{
			System.err.println("Log file not found:");
			fileNotFoundException.printStackTrace();
			return;
		}
		catch( IOException ioException)
		{
			System.err.println("An IO Exception has occurred:");
			ioException.printStackTrace();
			return;
		}
	}
}
