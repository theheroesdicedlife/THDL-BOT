package thdl.listeners;


import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import thdl.factories.discord.ThdlMemberFactory;


public class RemoveRoleFromMemberListener extends ListenerAdapter
{

	/**
	 * Occurs, if a role is removed from a member
	 * Gets the ThdlMember with getMember and actualizes his roles
	 */
	@Override
	public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event)
	{
		ThdlMemberFactory.getMember(event.getUser()).setRoles();
	}
}
