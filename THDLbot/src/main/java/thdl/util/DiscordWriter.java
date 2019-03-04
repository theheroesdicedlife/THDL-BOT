package thdl.util;


import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;


public class DiscordWriter
{

	private TextChannel		channel			= null;
	private Color			errorColor		= Color.RED;
	private Color			successColor	= Color.GREEN;
	private Color			infoColor		= Color.ORANGE;
	private EmbedBuilder	embedout		= null;

	public DiscordWriter(TextChannel txt)
	{
		this.setChannel(txt);
	}

	public DiscordWriter(GuildMessageReceivedEvent event)
	{
		this.channel = event.getChannel();
	}

	/**
	 * Writes an Error-Message in the Error-Message-Color. Standard = Red
	 * 
	 * @param msg
	 */
	public void writeError(String msg)
	{
		if (this.channel != null)
		{
			embedout = new EmbedBuilder().setColor(errorColor);
			embedout.setDescription(msg);
			channel.sendMessage(embedout.build()).queue();
			embedout = null;
		}
	}

	/**
	 * Writes a Success-Message in the Success-Message-Color. Standard = Green
	 * 
	 * @param msg
	 */
	public void writeSuccess(String msg)
	{
		if (this.channel != null)
		{
			embedout = new EmbedBuilder().setColor(successColor);
			embedout.setDescription(msg);
			channel.sendMessage(embedout.build()).queue();
			embedout = null;
		}
	}

	/**
	 * Writes an Info-Message in the Info-Message-Color. Standard = Orange
	 * 
	 * @param msg
	 */
	public void writeInfo(String msg)
	{
		if (this.channel != null)
		{
			embedout = new EmbedBuilder().setColor(infoColor);
			embedout.setDescription(msg);
			channel.sendMessage(embedout.build()).queue();
			embedout = null;
		}
	}

	/**
	 * writes a message not embed
	 * 
	 * @param msg
	 */
	public void writeNotEmbed(String msg)
	{
		if (this.channel != null)
		{
			channel.sendMessage(msg).queue();
		}
	}

	/**
	 * Writes a custom-colored Message
	 * 
	 * @param msg
	 * @param c
	 */
	public void writeCustomColored(String msg, Color c)
	{
		if (this.channel != null)
		{
			embedout = new EmbedBuilder().setColor(c);
			embedout.setDescription(msg);
			channel.sendMessage(embedout.build()).queue();
			embedout = null;
		}
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
