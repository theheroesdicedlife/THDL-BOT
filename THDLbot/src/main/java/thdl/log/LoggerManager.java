package thdl.log;


import java.util.ArrayList;


public class LoggerManager
{

	private static ArrayList<Logger> loggerC = new ArrayList<Logger>();

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

	private static Logger createLogger(String logNum, String logName)
	{
		return new Logger(logNum, logName);
	}

}
