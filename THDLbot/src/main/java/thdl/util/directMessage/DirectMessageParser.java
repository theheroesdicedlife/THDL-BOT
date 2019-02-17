package thdl.util.directMessage;


import java.util.ArrayList;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;


public class DirectMessageParser
{

	private String						invoke	= "";
	private PrivateMessageReceivedEvent	event	= null;
	private String[]					args	= null;

	protected PrivateMessageReceivedEvent getEvent()
	{
		return event;
	}

	protected String getInvoke()
	{
		return invoke;
	}

	protected String[] getArgs()
	{
		return args;
	}

	public DirectMessageParser(String raw, PrivateMessageReceivedEvent event)
	{
		this.event = event;
		this.parse(raw);
	}

	/**
	 * Taking the raw command apart to invoke and its arguments
	 * 
	 * @param raw
	 * @param event
	 */
	private void parse(String raw)
	{
		String split[] = raw.split(" ");

		invoke = split[0];

		ArrayList<String> splitList = new ArrayList<String>();

		for (String s : split)
		{
			splitList.add(s);
		}

		args = new String[splitList.size() - 1];

		splitList.subList(1, splitList.size()).toArray(args);
	}
}
