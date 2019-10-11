package thdl.util.directMessage;


import java.util.HashMap;
import thdl.bot.ILogMain;
import thdl.bot.IMainUtil;
import thdl.commands.directMessage.DirectCommand;
import thdl.util.DirectWriter;
import thdl.util.log.LogMessageType;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class DirectMessageHandler
{

	/*
	 * Class
	 * Singleton-Pattern
	 */
	private static DirectMessageHandler instance = null;

	public static DirectMessageHandler getInstance()
	{
		if (instance == null)
		{
			instance = new DirectMessageHandler();
		}

		return instance;
	}

	/*
	 * Object
	 */
	private HashMap<String, DirectCommand> commands = null;

	private DirectMessageHandler()
	{

		commands = new HashMap<String, DirectCommand>();

	}

	public HashMap<String, DirectCommand> getCommands()
	{
		return commands;
	}

	public void handleCommand(DirectMessageParser parser) throws Exception
	{
		Logger log = LoggerManager.getLogger(ILogMain.NUM, ILogMain.NAME);
		DirectWriter writer = null;
		writer = new DirectWriter(parser.getEvent().getAuthor());

		System.out.println(parser.getInvoke());
		log.logState(ILogMain.DIRECT_MSG_HANDLER, ILogMain.DIRECT_COMMAND);

		if (getCommands().containsKey(parser.getInvoke()))
		{
			boolean safe = true;
			DirectCommand cmd = getCommands().get(parser.getInvoke());
			safe = cmd.called(parser.getArgs(), parser.getEvent());
			if (safe)
			{
				cmd.action(parser.getArgs(), parser.getEvent());
				cmd.executed(safe, parser.getEvent());
			}
			else
			{
				cmd.executed(safe, parser.getEvent());
			}
		}
		else
		{
			log.addMessageToLog(ILogMain.DIRECT_MSG_HANDLER, LogMessageType.ERROR, ILogMain.COMMAND_UNKNOWN,
					IMainUtil.NOT_IN_CMD_TABLE);
			writer.writeMsg(IMainUtil.NOT_A_DM_CMD);
		}
	}
}
