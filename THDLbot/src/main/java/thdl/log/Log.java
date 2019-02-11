package thdl.log;


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

	protected LogObject createLogObject(String causeObject, LogMessageType type, String cause, String message)
	{
		LogObject lo = null;
		lo = new LogObject(this.nextLogObjNumber(), causeObject, type, cause, message);
		return lo;
	}

	protected void addLogObject(LogObject lo)
	{
		if (logEntries != null)
		{
			logEntries.add(lo);
			logEntries.sort(
					(lo1, lo2) -> Integer.valueOf(lo1.getLogNumber()).compareTo(Integer.valueOf(lo2.getLogNumber())));
		}
	}

	public void clearEntries()
	{
		if (logEntries != null)
		{
			logEntries.clear();
		}
	}

	public void printLog()
	{
		String output = "";
		output = "Log: " + this.logName;
		output = output + "Number: " + logNum + "/n/n";
		for (LogObject lo : logEntries)
		{
			output = output + "Entry: " + lo.getLogNumber();
			output = output + " - Cause: " + lo.getCauseObject() + ", " + lo.getCause();
			output = output + " -> Message: " + lo.getMessage() + "/n";
		}

		System.out.println(output);
	}

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
