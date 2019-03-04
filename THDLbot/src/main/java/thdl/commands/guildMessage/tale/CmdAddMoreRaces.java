package thdl.commands.guildMessage.tale;


import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import thdl.commands.guildMessage.Command;
import thdl.util.DiscordWriter;
import thdl.util.log.Logger;


public class CmdAddMoreRaces implements Command
{

	private DiscordWriter	writer	= null;
	private Logger			log		= null;

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		writer = new DiscordWriter(e);

		return false;
	}

	@Override
	public void action(String[] args, GuildMessageReceivedEvent e) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String help()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
