package thdl.lib.discord;


import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import thdl.lib.collections.discord.RoleCollection;
import thdl.util.Static;


public class ThdlMember
{

	private Member			discordMember;
	private RoleCollection	rolesOfMember	= null;

	public ThdlMember(Member discordMember)
	{
		this.discordMember = discordMember;
		rolesOfMember = new RoleCollection();
	}

	public boolean isGuest()
	{
		return rolesOfMember.containsRoleWithID(Static.NOT_ALLOWED_ROLE_ID);
	}

	public boolean isStoryteller()
	{
		return rolesOfMember.containsRoleWithID(Static.STORYTELLER_ROLE_ID);
	}

	public boolean isAllowed()
	{
		return rolesOfMember.containsRoleWithID(Static.ALLOWED_ROLE_ID);
	}

	public boolean isAdmin()
	{
		return rolesOfMember.containsRoleWithID(Static.ADMIN_ROLE_ID);
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
			rolesOfMember.addRole(r);
		}
	}
}
