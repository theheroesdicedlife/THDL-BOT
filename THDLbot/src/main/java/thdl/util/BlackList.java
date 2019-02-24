package thdl.util;


import java.util.ArrayList;
import net.dv8tion.jda.api.entities.User;


public class BlackList
{

	private static ArrayList<String> blackList = new ArrayList<String>();

	public static void addEntry(User user)
	{
		String id = user.getId();
		if (!blackList.contains(id))
		{
			blackList.add(id);
		}
	}

	public static boolean isBlocked(User user)
	{
		return blackList.contains(user.getId());
	}
}
