package thdl.rpg.lib;

import java.util.ArrayList;

public abstract class Jobs
{
	private static ArrayList<String> startJobs = new ArrayList<>();

	public static ArrayList<String> getStartJobs()
	{
		return startJobs;
	}

	public static boolean isThisAJob(String jobName)
	{
		return startJobs.contains(jobName);
	}
}
