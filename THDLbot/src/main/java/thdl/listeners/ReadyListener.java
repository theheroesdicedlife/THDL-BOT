package thdl.listeners;


import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import thdl.lib.factories.discord.RoleFactory;
import thdl.lib.factories.discord.TextChannelFactory;
import thdl.lib.factories.discord.ThdlMemberFactory;
import thdl.lib.factories.discord.VoiceChannelFactory;
import thdl.util.IDiscordID;


public class ReadyListener extends ListenerAdapter
{

	/**
	 * Occurs, if the bot is ready for input
	 * Scans the connected Guild
	 * Loads some data from the database
	 */
	@Override
	public void onReady(ReadyEvent event)
	{
		Guild host = event.getJDA().getGuildById(IDiscordID.GUILD_ID);

		ThdlMemberFactory.getInstance().createMapOfMembers(host);
		RoleFactory.getInstance().createRoleMap(host);
		TextChannelFactory.getInstance().createTextChannelMap(host);
		VoiceChannelFactory.getInstance().createVoiceChannelMap(host);
		// Loader aLoader = Loader.getInstance();

		// try
		// {
		// aLoader.loadRaces();
		// aLoader.loadJobs();
		// aLoader.loadTales();
		// }
		// catch (SQLException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

}
