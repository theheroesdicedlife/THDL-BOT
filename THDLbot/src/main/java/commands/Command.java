package commands;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import thdl.bot.DiscordWriter;

public interface Command
{
	boolean called(String args[], GuildMessageReceivedEvent e);

	void action(String args[], GuildMessageReceivedEvent e);

	void executed(boolean success, GuildMessageReceivedEvent event);

	String help();

	DiscordWriter openWriter(GuildMessageReceivedEvent e);
}
