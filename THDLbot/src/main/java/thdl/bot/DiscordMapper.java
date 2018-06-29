package thdl.bot;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

public class DiscordMapper
{
	public static HashMap<String, Role>		RoleOverName	= new HashMap<>();
	public static HashMap<String, Member>	MemberOverID	= new HashMap<>();

	public static void createMapOfGuild(Guild hostGuild)
	{
		for (Role r : hostGuild.getRoles())
		{
			if (!RoleOverName.containsKey(r.getName()))
			{
				RoleOverName.put(r.getName(), r);
			}
		}

		for (Member m : hostGuild.getMembers())
		{
			if (!MemberOverID.containsKey(m.getUser().getId()))
			{
				MemberOverID.put(m.getUser().getId(), m);
			}
		}

		System.out.println("Map finished");
	}

	public static boolean isThisA(Member m, String rolename)
	{
		boolean itIs = false;

		List<Role> mRoles = m.getRoles();
		Role test = RoleOverName.get(rolename);

		if (test != null)
		{
			for (Role r : mRoles)
			{
				if (r.getId().compareTo(test.getId()) == 0)
				{
					itIs = true;
					break;
				}
			}
		}
		else
		{
			itIs = false;
		}
		return itIs;
	}

	public static Member getMember(String nick)
	{
		Member member = null;
		Collection<Member> memberCol = MemberOverID.values();
		for (Member m : memberCol)
		{
			String memberNick = m.getNickname();
			if (memberNick.equals(nick))
			{
				member = m;
			}
		}
		return member;
	}
}
