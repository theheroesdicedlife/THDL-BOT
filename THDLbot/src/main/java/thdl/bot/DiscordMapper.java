package thdl.bot;

import java.util.HashMap;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;

public class DiscordMapper
{
	public static HashMap<String, Role> RoleOverName = new HashMap<>();

	public static void createMapOfGuild(Guild hostGuild)
	{
		for (Role r : hostGuild.getRoles())
		{
			if (!RoleOverName.containsKey(r.getName()))
			{
				RoleOverName.put(r.getName(), r);
			}
		}

		System.out.println("Map finished");
	}
}
