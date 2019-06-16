package thdl.commands.directMessage.tale;


import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.managers.GuildController;
import thdl.lib.discord.ThdlMember;
import thdl.lib.rpg.Tale;
import thdl.lib.rpg.Turn;


public class InviteHandler
{

	/**
	 * This class is handling the accept and decline of invitations
	 */

	// ACCEPT

	/**
	 * This method is called, if the invite was accepted by the member
	 * 
	 * @param member
	 *            who accepted the invitation
	 * @param tale
	 *            the player was invited to and will join now
	 * @param controller
	 *            of the guild the bot is attached to
	 * @throws Exception
	 *             can occur while adding the role of the tale to the new player
	 */
	public void handleAccept(ThdlMember member, Tale tale, GuildController controller) throws Exception
	{
		try
		{
			giveRole(member, tale, controller);
			createTurn(member, tale);
			removeInvite(member, tale);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * Adds the role of the tale to the member of the new player
	 * 
	 * @param member
	 *            who accepted the invitation
	 * @param tale
	 *            the player was invited to and will join now
	 * @param controller
	 *            of the guild the bot is attached to
	 * @throws Exception
	 *             can occur while adding the role of the tale to the new player
	 */
	private void giveRole(ThdlMember member, Tale tale, GuildController controller) throws Exception
	{
		Member dm = member.getMember();
		Role role = tale.getTaleRole();

		try
		{
			controller.addRolesToMember(dm, role).queue();
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * Creates a new Turn-Object which gets added by both the member and the tale
	 * 
	 * @param member
	 *            who accepted the invitation
	 * @param tale
	 *            the player was invited to
	 */
	private void createTurn(ThdlMember member, Tale tale)
	{
		Turn turn = new Turn(tale, member);
		tale.addTurn(turn);
		member.addTurn(turn);
	}

	// BOTH

	/**
	 * Removes the invite from the member
	 * 
	 * @param member
	 *            who accepted or declined the invitation
	 * @param tale
	 *            the player was invited to
	 */
	private void removeInvite(ThdlMember member, Tale tale)
	{
		member.removeInvite(tale);
	}
}
