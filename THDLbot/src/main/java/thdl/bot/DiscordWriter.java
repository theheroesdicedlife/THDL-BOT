package thdl.bot;

import java.awt.Color;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class DiscordWriter
{
	private GuildMessageReceivedEvent	event			= null;
	private TextChannel					channel			= null;
	private Color						errorColor		= Color.RED;
	private Color						successColor	= Color.GREEN;
	private Color						infoColor		= Color.ORANGE;
	private EmbedBuilder				embedout		= null;

	public DiscordWriter(TextChannel txt)
	{
		this.setChannel(txt);
	}

	public DiscordWriter(GuildMessageReceivedEvent e)
	{
		this.setEvent(e);
		this.setChannel(e.getChannel());
	}

	public void writeError(String msg)
	{
		embedout = new EmbedBuilder().setColor(errorColor);
		embedout.setDescription(msg);
		channel.sendMessage(embedout.build()).queue();
		embedout = null;
	}

	public void writeSuccess(String msg)
	{
		embedout = new EmbedBuilder().setColor(successColor);
		embedout.setDescription(msg);
		channel.sendMessage(embedout.build()).queue();
		embedout = null;
	}

	public void writeInfo(String msg)
	{
		embedout = new EmbedBuilder().setColor(infoColor);
		embedout.setDescription(msg);
		channel.sendMessage(embedout.build()).queue();
		embedout = null;
	}

	public void writeNotEmbed(String msg)
	{
		channel.sendMessage(msg).queue();
	}

	public void writeCustomColored(String msg, Color c)
	{
		embedout = new EmbedBuilder().setColor(c);
		embedout.setDescription(msg);
		channel.sendMessage(embedout.build()).queue();
		embedout = null;
	}

	public GuildMessageReceivedEvent getEvent()
	{
		return event;
	}

	private void setEvent(GuildMessageReceivedEvent event)
	{
		this.event = event;
	}

	public TextChannel getChannel()
	{
		return channel;
	}

	private void setChannel(TextChannel channel)
	{
		this.channel = channel;
	}

	public Color getErrorColor()
	{
		return errorColor;
	}

	public void setErrorColor(Color errorColor)
	{
		this.errorColor = errorColor;
	}

	public Color getSuccessColor()
	{
		return successColor;
	}

	public void setSuccessColor(Color successColor)
	{
		this.successColor = successColor;
	}

	public Color getInfoColor()
	{
		return infoColor;
	}

	public void setInfoColor(Color infoColor)
	{
		this.infoColor = infoColor;
	}
}
