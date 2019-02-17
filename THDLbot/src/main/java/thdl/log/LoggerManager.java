package thdl.log;


import java.util.ArrayList;


public class LoggerManager
{

	private static ArrayList<Logger> loggerC = new ArrayList<Logger>();

	/**
	 * Returns the logger for the specific log-Number or creates a new Logger if
	 * there is non
	 * 
	 * @param logNum
	 * @param logName
	 * @return
	 */
	public static Logger getLogger(String logNum, String logName)
	{
		Logger reLog = null;
		for (Logger log : loggerC)
		{
			if (log.getLogNum().equals(logNum))
			{
				reLog = log;
				break;
			}
		}

		if (reLog == null)
		{
			reLog = createLogger(logNum, logName);
		}

		return reLog;
	}

	/**
	 * Creates a new Logger for the specific number and the specific name
	 * 
	 * @param logNum
	 * @param logName
	 * @return
	 */

	private static Logger createLogger(String logNum, String logName)
	{
		return new Logger(logNum, logName);
	}

}
