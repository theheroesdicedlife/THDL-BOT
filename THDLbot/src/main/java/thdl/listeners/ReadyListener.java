package thdl.listeners;

import java.sql.SQLException;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import thdl.bot.DiscordMapper;
import thdl.bot.Loader;
import thdl.util.DiscordID;

public class ReadyListener extends ListenerAdapter
{
	public void onReady(ReadyEvent event)
	{
		DiscordMapper.setHostServer(event.getJDA().getGuildById(DiscordID.GUILD_ID));
		DiscordMapper.createMapOfGuild();
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
