package thdl.listeners.role;


import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import thdl.lib.factories.discord.RoleFactory;


public class RoleDeletionListener extends ListenerAdapter
{

	/**
	 * Occurs, if a role gets deleted from the guild
	 */
	@Override
	public void onRoleDelete(RoleDeleteEvent event)
	{
		RoleFactory.resetRoleMap();
		RoleFactory.createRoleMap(event.getGuild());
	}
}
