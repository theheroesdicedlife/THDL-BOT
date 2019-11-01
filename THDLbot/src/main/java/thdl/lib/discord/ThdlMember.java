package thdl.lib.discord;


import java.util.ArrayList;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import thdl.lib.collections.discord.RoleCollection;
import thdl.lib.rpg.Tale;
import thdl.lib.rpg.Turn;
import thdl.util.IDiscordID;


public class ThdlMember
{

	// discord
	private Member			discordMember;
	private RoleCollection	rolesOfMember	= null;

	// in tale
	private ArrayList<Turn> turnInTale = null;

	// invite
	private ArrayList<Tale> invitedTo = null;

	// CONSTRUCTOR

	public ThdlMember(Member discordMember)
	{
		this.discordMember = discordMember;
		rolesOfMember = new RoleCollection();
		invitedTo = new ArrayList<Tale>();
		turnInTale = new ArrayList<Turn>();
	}

	// ADDER

	/**
	 * 
	 * @param turn
	 *            turn for a single tale of the player
	 */
	public void addTurn(Turn turn)
	{
		turnInTale.add(turn);
	}

	// INVITE TO TALE

	/**
	 * Adds an invited for a tale to the member
	 * 
	 * @param tale
	 *            is the tale the member was invited to
	 */
	public void addInvitedTo(Tale tale)
	{
		if (!invitedTo.contains(tale))
		{
			invitedTo.add(tale);
		}
	}

	/**
	 * 
	 * @param tale
	 *            the tale to check
	 * @return
	 * 		is the member invited to the tale
	 */
	public boolean isInvitedTo(Tale tale)
	{
		if (invitedTo.contains(tale))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 
	 * @param taleName
	 *            is the name given by the accept or decline command
	 * @return
	 * 		the tale for the talename from the invited list or null if the tale
	 *         was not found
	 */
	public Tale getInvitedTo(String taleName)
	{
		Tale invited = null;

		for (Tale tale : invitedTo)
		{
			if (tale.getTaleName().equals(taleName))
			{
				invited = tale;
				break;
			}
		}

		return invited;
	}

	public void removeInvite(Tale tale)
	{

		invitedTo.remove(tale);

		// boolean found = false;
		// int index = -1;
		//
		// for (Tale t : invitedTo)
		// {
		// index++;
		// if (t.getTaleName().equals(tale.getTaleName()))
		// {
		// found = true;
		// break;
		// }
		// else
		// {
		// found = false;
		// }
		// }
		//
		// if (found)
		// {
		// invitedTo.remove(index);
		// }
	}

	// ROLE-FLAGS

	/**
	 * @return
	 * 		is the thdl-member a allowed storyteller for thdl
	 */
	public boolean isDungeonmaster()
	{
		return rolesOfMember.containsRoleWithID(IDiscordID.DUNGEONMASTER_ROLE_ID);
	}

	/**
	 * @return
	 * 		is the thdl-member allowed to use the commands of this bot
	 */
	public boolean isAllowed()
	{
		return rolesOfMember.containsRoleWithID(IDiscordID.ALLOWED_ROLE_ID);
	}

	/**
	 * 
	 * @return
	 * 		is the thdl-member an admin for this bot
	 */
	public boolean isAdmin()
	{
		return rolesOfMember.containsRoleWithID(IDiscordID.ADMIN_ROLE_ID);
	}

	// GETTER + SETTER

	/**
	 * @return
	 * 		user-id of the discord-user for this thdl-member
	 */
	public String getUserID()
	{
		return discordMember.getUser().getId();
	}

	/**
	 * 
	 * @return
	 * 		the member for discord
	 */
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

	/**
	 * 
	 * @return
	 * 		all turns for tales of the player
	 */
	public ArrayList<Turn> getTurnInTale()
	{
		return turnInTale;
	}
}
