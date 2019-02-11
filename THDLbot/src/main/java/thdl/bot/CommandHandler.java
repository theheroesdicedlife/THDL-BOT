package thdl.bot;


import java.util.HashMap;
import thdl.commands.guildMessage.Command;
import thdl.log.LogMessageType;
import thdl.log.Logger;
import thdl.log.LoggerManager;


public class CommandHandler
{

	/**
	 * If a command is written in a Text-Channel of a guild,
	 * then it will be handled here
	 * First it gets checked, if the usage of the command is ok
	 * If it is, it will be executed
	 */
	public static final CommandParser		parse		= new CommandParser();
	public static HashMap<String, Command>	commands	= new HashMap<String, Command>();

	public static void handleCommand(CommandParser.commandContainer cmd) throws Exception
	{
		Logger log = LoggerManager.getLogger(ILogMain.NUM, ILogMain.NAME);

		System.out.println(cmd.invoke);
		log.logState(ILogMain.COMMAND_HANLDER, ILogMain.COMMAND);

		if (commands.containsKey(cmd.invoke))
		{
			boolean safe = true;
			safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);
			if (safe)
			{
				commands.get(cmd.invoke).action(cmd.args, cmd.event);
				commands.get(cmd.invoke).executed(safe, cmd.event);
			}
			else
			{
				commands.get(cmd.invoke).executed(safe, cmd.event);
			}
		}
		else
		{
			log.addMessageToLog(ILogMain.COMMAND_HANLDER, LogMessageType.ERROR, ILogMain.COMMAND_UNKNOWN,
					IMainUtil.NOT_IN_CMD_TABLE);
		}
	}
}
