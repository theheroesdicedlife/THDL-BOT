package thdl.bot;


import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;


public class DiscordWriter
{

	private GuildMessageReceivedEvent	event			= null;
	private TextChannel					channel			= null;
	private Color						errorColor		= Color.RED;
	private Color						successColor	= Color.GREEN;
	private Color						infoColor		= Color.ORANGE;
	private EmbedBuilder				embedout		= null;

	private DiscordWriter(TextChannel txt)
	{
		this.setChannel(txt);
	}

	/**
	 * Creates a new DiscordWriter using a Textchannel
	 * 
	 * @param writer
	 *            DiscordWriter object that needs an instance
	 * @param txt
	 *            Textchannel the writer will write into
	 * @return
	 */
	public static DiscordWriter createWriter(DiscordWriter writer, TextChannel txt)
	{
		if (writer == null)
		{
			writer = new DiscordWriter(txt);
		}
		return writer;
	}

	/**
	 * Creates a new DiscordWriter using an Event
	 * 
	 * @param writer
	 *            DiscordWriter object that needs an instance
	 * @param event
	 *            Event to get the Textchannel the writer will write into
	 * @return
	 */
	public static DiscordWriter createWriter(DiscordWriter writer, GuildMessageReceivedEvent event)
	{
		if (writer == null)
		{
			writer = new DiscordWriter(event.getChannel());
		}
		writer.setEvent(event);
		return writer;
	}

	/**
	 * Writes an Error-Message in the Error-Message-Color. Standard = Red
	 * 
	 * @param msg
	 */
	public void writeError(String msg)
	{
		embedout = new EmbedBuilder().setColor(errorColor);
		embedout.setDescription(msg);
		channel.sendMessage(embedout.build()).queue();
		embedout = null;
	}

	/**
	 * Writes a Success-Message in the Success-Message-Color. Standard = Green
	 * 
	 * @param msg
	 */
	public void writeSuccess(String msg)
	{
		embedout = new EmbedBuilder().setColor(successColor);
		embedout.setDescription(msg);
		channel.sendMessage(embedout.build()).queue();
		embedout = null;
	}

	/**
	 * Writes an Info-Message in the Info-Message-Color. Standard = Orange
	 * 
	 * @param msg
	 */
	public void writeInfo(String msg)
	{
		embedout = new EmbedBuilder().setColor(infoColor);
		embedout.setDescription(msg);
		channel.sendMessage(embedout.build()).queue();
		embedout = null;
	}

	/**
	 * writes a message not embed
	 * 
	 * @param msg
	 */
	public void writeNotEmbed(String msg)
	{
		channel.sendMessage(msg).queue();
	}

	/**
	 * Writes a custom-colored Message
	 * 
	 * @param msg
	 * @param c
	 */
	public void writeCustomColored(String msg, Color c)
	{
		embedout = new EmbedBuilder().setColor(c);
		embedout.setDescription(msg);
		channel.sendMessage(embedout.build()).queue();
		embedout = null;
	}

	/**
	 * Writes a private Message to a User
	 * 
	 * @param msg
	 * @param reciever
	 *            User who receives the private Message
	 */
	public void writePrivate(String msg, User reciever)
	{
		PrivateChannel privChan = reciever.openPrivateChannel().complete();
		privChan.sendMessage(msg).queue();
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
