package thdl.util.log;


public class Logger
{

	private Log		log	= null;
	private String	logNum;
	private String	logName;

	protected Logger(String logNum, String logName)
	{
		this.logNum = logNum;
		this.logName = logName;
		createLog();
	}

	/**
	 * Creates a new Log with the specific name and number
	 */
	private void createLog()
	{
		if (log == null)
		{
			log = new Log(logName, logNum);
		}
	}

	public String getLogNum()
	{
		return logNum;
	}

	public Log getLog()
	{
		if (log != null)
		{
			return log;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Adds a new Message to the Log of this Logger
	 * 
	 * @param causeObject
	 *            The Object or Class causing the Message
	 * @param type
	 *            The Type of the Message
	 * @param cause
	 *            The cause why the Message needs to be logged
	 * @param message
	 *            The Message to log
	 */
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

	/**
	 * Logs a statement as cause, without a message
	 * 
	 * @param causeObject
	 *            The object or Class causing the statement
	 * @param cause
	 *            The statement
	 */
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

	/**
	 * Logs an error without a message
	 * 
	 * @param causeObject
	 *            is the Object or Class causing the error
	 * @param cause
	 *            is the cause of the error
	 */
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

	/**
	 * Logs an information
	 * 
	 * @param causeObject
	 *            is the Object or Class which is informing
	 * @param cause
	 *            is the cause of the information
	 * @param msg
	 *            is the information
	 */
	public void logInfo(String causeObject, String cause, String msg)
	{
		if (!causeObject.equals("") && !cause.equals("") && !msg.equals(""))
		{
			if (log != null)
			{
				LogObject lo = log.createLogObject(causeObject, LogMessageType.INFO, cause, msg);
				log.addLogObject(lo);
			}
		}
	}

	/**
	 * Logs a try-catch-exception
	 * 
	 * @param causeObject
	 *            is the Object or Class in which the exception was caught
	 * @param cause
	 *            is the cause why the exception was thrown
	 * @param msg
	 *            is the message given by exception.getMessage()
	 */
	public void logException(String causeObject, String cause, String msg)
	{
		if (!causeObject.equals("") && !cause.equals("") && !msg.equals(""))
		{
			if (log != null)
			{
				LogObject lo = log.createLogObject(causeObject, LogMessageType.EXCEPTION, cause, msg);
				log.addLogObject(lo);
			}
		}
	}
}
