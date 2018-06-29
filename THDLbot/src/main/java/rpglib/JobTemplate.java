package rpglib;

import java.util.HashMap;

public abstract class JobTemplate
{
	private static HashMap<String, Job>	startJobs	= new HashMap<>();
	private static HashMap<String, Job>	firstJobs	= new HashMap<>();
	private static HashMap<String, Job>	secondJobs	= new HashMap<>();
	private static HashMap<String, Job>	thirdJobs	= new HashMap<>();
	private static HashMap<String, Job>	masterJobs	= new HashMap<>();

	public static Job getStartJob(String name)
	{
		if (startJobs.containsKey(name))
		{
			return startJobs.get(name);
		}
		else
		{
			return null;
		}
	}
}
