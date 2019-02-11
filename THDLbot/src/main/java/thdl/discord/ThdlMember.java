package thdl.discord;


import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import thdl.util.Static;


public class ThdlMember
{

	private Member	discordMember;
	private boolean	isStoryteller	= false;
	private boolean	isAllowed		= false;
	private boolean	isGuest			= true;

	public ThdlMember(Member discordMember)
	{
		this.discordMember = discordMember;
	}

	public boolean isGuest()
	{
		return isGuest;
	}

	public boolean isStoryteller()
	{
		return isStoryteller;
	}

	public boolean isAllowed()
	{
		return isAllowed;
	}

	public Member getMember()
	{
		return this.discordMember;
	}

	/**
	 * Sets the booleans for the roles of a member object
	 */
	public void setRoles()
	{
		for (Role r : discordMember.getRoles())
		{
			if (r.getName().equals(Static.NOT_ALLOWED_ROLE))
			{
				isGuest = true;
			}
			else
			{
				isGuest = false;
			}
			if (r.getName().equals(Static.ALLOWED_ROLE))
			{
				isAllowed = true;
			}
			else
			{
				isAllowed = false;
			}
			if (r.getName().equals(Static.STORYTELLER_ROLE))
			{
				isStoryteller = true;
			}
			else
			{
				isStoryteller = false;
			}
		}
	}
}
