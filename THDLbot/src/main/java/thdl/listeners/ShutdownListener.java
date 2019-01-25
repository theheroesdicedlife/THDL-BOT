package thdl.listeners;

import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import thdl.bot.DatabaseConnect;
import thdl.rpg.lib.Tales;

public class ShutdownListener extends ListenerAdapter
{
	public void onShutdown(ShutdownEvent e)
	{
		DatabaseConnect dbconnect = DatabaseConnect.getInstance();
		Tales.saveTales();

		dbconnect.closeDB();
	}
}
