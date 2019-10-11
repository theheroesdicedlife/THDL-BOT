package thdl.util.guildMessage;


import java.util.ArrayList;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import thdl.util.IStatic;


public class CommandParser
{

	private String						beheaded	= "";
	private String[]					args		= null;
	private String						invoke		= "";
	private GuildMessageReceivedEvent	event		= null;

	/**
	 * @return the beheaded Command
	 */
	public String getBeheaded()
	{
		return beheaded;
	}

	/**
	 * @return the args of the Command
	 */
	public String[] getArgs()
	{
		return args;
	}

	/**
	 * @return the invoke for the Command
	 */
	public String getInvoke()
	{
		return invoke;
	}

	/**
	 * @return the event that gets handled
	 */
	public GuildMessageReceivedEvent getEvent()
	{
		return event;
	}

	public CommandParser(String raw, GuildMessageReceivedEvent event)
	{
		this.event = event;
		parse(raw);
	}

	/**
	 * Takes apart the command-String coming from Discord
	 * 
	 * @param raw
	 *            The raw command-String unparsed
	 */
	public void parse(String raw)
	{

		beheaded = "";
		invoke = "";
		args = null;

		beheaded = raw.replaceFirst(IStatic.PREFIX, "");

		String beheadedSplit[] = beheaded.split(" ");

		invoke = beheadedSplit[0];

		args = new String[beheadedSplit.length - 1];

		ArrayList<String> split = new ArrayList<String>();

		for (String s : beheadedSplit)
		{
			split.add(s);
		}

		split.subList(1, split.size()).toArray(args);

	}

}
