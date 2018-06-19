package listeners;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import thdl.bot.DiscordMapper;
import util.DiscordID;

public class ReadyListener extends ListenerAdapter
{
	public void onReady(ReadyEvent event)
	{
		DiscordMapper.createMapOfGuild(event.getJDA().getGuildById(DiscordID.GUILD_ID));
	}

}
