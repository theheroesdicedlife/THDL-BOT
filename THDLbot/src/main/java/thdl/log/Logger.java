package thdl.log;


public class Logger
{

	private Log		log	= null;
	private String	logNum;
	private String	logName;

	protected Logger(String logNum, String logName)
	{
		createLog();
	}

	private void createLog()
	{
		if (log == null)
		{
			log = new Log(this, logName, logNum);
		}
	}

	public String getLogNum()
	{
		return logNum;
	}

	public void addMessageToLog(String causeObject, LogMessageType type, String cause, String message)
	{
		if (!causeObject.equals("") && !message.equals("") && !cause.equals(""))
		{
			if (log != null)
			{
				LogObject lo = log.createLogObject(causeObject, type, cause, message);
				log.addLogObject(lo);
			}
		}
	}

	public void logState(String causeObject, String cause)
	{
		if (!causeObject.equals("") && !cause.equals(""))
		{
			if (log != null)
			{
				LogObject lo = log.createLogObject(causeObject, LogMessageType.STATE, cause, "");
				log.addLogObject(lo);
			}
		}
	}

	public void logErrorWithoutMsg(String causeObject, String cause)
	{
		if (!causeObject.equals("") && !cause.equals(""))
		{
			if (log != null)
			{
				LogObject lo = log.createLogObject(causeObject, LogMessageType.ERROR, cause, "");
				log.addLogObject(lo);
			}
		}
	}
}
