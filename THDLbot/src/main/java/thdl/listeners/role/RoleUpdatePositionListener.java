package thdl.listeners.role;


import net.dv8tion.jda.api.events.role.update.RoleUpdatePositionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import thdl.lib.factories.discord.RoleFactory;


public class RoleUpdatePositionListener extends ListenerAdapter
{

	/**
	 * Occurs, if the position of a role gets changed
	 */
	@Override
	public void onRoleUpdatePosition(RoleUpdatePositionEvent event)
	{
		RoleFactory roleF = RoleFactory.getInstance();

		roleF.resetRoleMap();
		roleF.createRoleMap(event.getGuild());
	}
}
