package thdl.listeners;


import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import thdl.bot.DatabaseConnect;
import thdl.rpg.lib.Tales;


public class ShutdownListener extends ListenerAdapter
{

	/**
	 * Occurs, if the bot is shutdowned
	 */
	@Override
	public void onShutdown(ShutdownEvent e)
	{
		DatabaseConnect dbconnect = DatabaseConnect.getInstance();
		Tales.saveTales();

		dbconnect.closeDB();
	}
}
