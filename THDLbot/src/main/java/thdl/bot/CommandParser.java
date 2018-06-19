package thdl.bot;

import java.util.ArrayList;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import util.Static;;

public class CommandParser
{
	public commandContainer parser(String raw, GuildMessageReceivedEvent event)
	{
		String beheaded = raw.replaceFirst(Static.PREFIX, "");

		String beheadedSplit[] = beheaded.split(" ");

		String invoke = beheadedSplit[0];

		ArrayList<String> split = new ArrayList<String>();

		for (String s : beheadedSplit)
		{
			split.add(s);
		}

		String args[] = new String[split.size() - 1];

		split.subList(1, split.size()).toArray(args);

		return new commandContainer(raw, beheaded, beheadedSplit, invoke, args, event);
	}

	public static class commandContainer
	{

		public final String						raw;

		public final String						beheaded;

		public final String[]					splitBeheaded;

		public final String						invoke;

		public final String[]					args;

		public final GuildMessageReceivedEvent	event;

		public commandContainer(String rw, String beheaded, String[] splitBeheaded, String invoke, String[] args,
		        GuildMessageReceivedEvent event)
		{
			this.raw = rw;
			this.beheaded = beheaded;
			this.splitBeheaded = splitBeheaded;
			this.invoke = invoke;
			this.args = args;
			this.event = event;
		}
	}
}
