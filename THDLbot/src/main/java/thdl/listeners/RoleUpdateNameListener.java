package thdl.listeners;


import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import thdl.lib.factories.discord.RoleFactory;


public class RoleUpdateNameListener extends ListenerAdapter
{

	/**
	 * Occurs, if the name of a role gets changed
	 */
	@Override
	public void onRoleUpdateName(RoleUpdateNameEvent event)
	{
		RoleFactory.resetRoleMap();
		RoleFactory.createRoleMap(event.getGuild());
	}
}
