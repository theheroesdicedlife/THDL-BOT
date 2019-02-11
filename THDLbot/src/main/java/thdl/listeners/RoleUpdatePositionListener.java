package thdl.listeners;


import net.dv8tion.jda.api.events.role.update.RoleUpdatePositionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import thdl.factories.discord.RoleFactory;


public class RoleUpdatePositionListener extends ListenerAdapter
{

	/**
	 * Occurs, if the position of a role gets changed
	 */
	@Override
	public void onRoleUpdatePosition(RoleUpdatePositionEvent event)
	{
		RoleFactory.resetRoleMap();
		RoleFactory.createRoleMap(event.getGuild());
	}
}
