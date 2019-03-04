package thdl.listeners.member;


import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import thdl.lib.factories.discord.ThdlMemberFactory;


public class AddRoleToMemberListener extends ListenerAdapter
{

	/**
	 * Occurs, if a role is added to a member of the connected guild
	 * Calls the method getMember to actualize the thdl-roles of the Member
	 * 
	 * @param event
	 */
	@Override
	public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event)
	{
		ThdlMemberFactory.getMember(event.getUser()).setRoles();
	}
}
