package thdl.listeners.role;


import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import thdl.lib.factories.discord.RoleFactory;


public class RoleCreationListener extends ListenerAdapter
{

	/**
	 * Occurs, if a role is created
	 */
	@Override
	public void onRoleCreate(RoleCreateEvent event)
	{
		RoleFactory.resetRoleMap();
		RoleFactory.createRoleMap(event.getGuild());
	}
}
