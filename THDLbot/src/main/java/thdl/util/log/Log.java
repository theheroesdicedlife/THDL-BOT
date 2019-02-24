package thdl.util.log;


import java.util.ArrayList;


public class Log
{

	private int						logObjNumber;
	private String					logName;
	private String					logNum;
	private ArrayList<LogObject>	logEntries	= null;
	private Logger					logWriter	= null;

	protected Log(Logger logWriter, String logName, String logNum)
	{
		this.logWriter = logWriter;
		this.logName = logName;
		this.logNum = logNum;
		logEntries = new ArrayList<LogObject>();
		logObjNumber = 0;
	}

	public String getLogName()
	{
		return logName;
	}

	protected void setLogName(String logName)
	{
		this.logName = logName;
	}

	/**
	 * Creates a single Object for a Log
	 * 
	 * @param causeObject
	 *            The Object or class which is causing the Log-entry
	 * @param type
	 *            The type the message has
	 * @param cause
	 *            The cause, why the message has to be logged
	 * @param message
	 *            The Message, which is logged with this object
	 * @return
	 * 		Returns the new logObject
	 */
	protected LogObject createLogObject(String causeObject, LogMessageType type, String cause, String message)
	{
		LogObject lo = null;
		lo = new LogObject(this.nextLogObjNumber(), causeObject, type, cause, message);
		return lo;
	}

	/**
	 * Adds a LogObject to the Log and sorts the Objects over thier log-number
	 * 
	 * @param lo
	 *            The LogObject to add
	 */
	protected void addLogObject(LogObject lo)
	{
		if (logEntries != null)
		{
			logEntries.add(lo);
			logEntries.sort(
					(lo1, lo2) -> Integer.valueOf(lo1.getLogNumber()).compareTo(Integer.valueOf(lo2.getLogNumber())));
		}
	}

	/**
	 * Deletes all Entries in the Log.
	 */
	public void clearEntries()
	{
		if (logEntries != null)
		{
			logEntries.clear();
			logObjNumber = 0;
		}
	}

	/**
	 * Shows the Log outside of the Bot
	 * TODO: Print into a txt-file
	 */
	public void printLog()
	{
		String output = "";
		output = IStaticLog.LOG + this.logName;
		output = output + IStaticLog.NUM + logNum + IStaticLog.LINE_BREAK + IStaticLog.LINE_BREAK;
		for (LogObject lo : logEntries)
		{
			output = output + IStaticLog.ENTRY + lo.getLogNumber();
			output = output + IStaticLog.CAUSE + lo.getCauseObject() + IStaticLog.COMMA + lo.getCause();
			output = output + IStaticLog.MESSAGE + lo.getMessage() + IStaticLog.LINE_BREAK;
		}

		System.out.println(output);
	}

	/**
	 * Returns the Message for a specific LogObject-Number
	 * 
	 * @param id
	 * @return
	 */
	public String getLogMessage(int id)
	{
		String message = "";
		if (logEntries != null)
		{
			message = logEntries.get(id).getMessage();
		}
		return message;
	}

	private int nextLogObjNumber()
	{
		logObjNumber = logObjNumber + 1;
		return logObjNumber;
	}

}
