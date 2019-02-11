package thdl.listeners;


import java.sql.SQLException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import thdl.bot.Loader;
import thdl.factories.discord.RoleFactory;
import thdl.factories.discord.TextChannelFactory;
import thdl.factories.discord.ThdlMemberFactory;
import thdl.factories.discord.VoiceChannelFactory;
import thdl.util.DiscordID;


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
		Guild host = event.getJDA().getGuildById(DiscordID.GUILD_ID);

		ThdlMemberFactory.createMapOfMembers(host);
		RoleFactory.createRoleMap(host);
		TextChannelFactory.createTextChannelMap(host);
		VoiceChannelFactory.createVoiceChannelMap(host);
		Loader aLoader = Loader.getInstance();

		try
		{
			aLoader.loadRaces();
			aLoader.loadJobs();
			aLoader.loadTales();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
