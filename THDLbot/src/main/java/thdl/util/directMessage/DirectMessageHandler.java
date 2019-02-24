package thdl.util.directMessage;


import java.util.HashMap;
import thdl.bot.ILogMain;
import thdl.bot.IMainUtil;
import thdl.commands.directMessage.DirectCommand;
import thdl.util.DiscordWriter;
import thdl.util.log.LogMessageType;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class DirectMessageHandler
{

	public static HashMap<String, DirectCommand> commands = new HashMap<String, DirectCommand>();

	public static void handleCommand(DirectMessageParser parser) throws Exception
	{
		Logger log = LoggerManager.getLogger(ILogMain.NUM, ILogMain.NAME);
		DiscordWriter writer = null;
		writer = DiscordWriter.createWriter(writer);

		System.out.println(parser.getInvoke());
		log.logState(ILogMain.DIRECT_MSG_HANDLER, ILogMain.DIRECT_COMMAND);

		if (commands.containsKey(parser.getInvoke()))
		{
			boolean safe = true;
			DirectCommand cmd = commands.get(parser.getInvoke());
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
			writer.writePrivate(IMainUtil.NOT_A_DM_CMD, parser.getEvent().getAuthor());
		}
	}
}
