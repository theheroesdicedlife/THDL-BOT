package thdl.util.guildMessage;


import java.util.HashMap;
import thdl.bot.ILogMain;
import thdl.bot.IMainUtil;
import thdl.commands.guildMessage.Command;
import thdl.util.log.LogMessageType;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class CommandHandler
{

	/*
	 * Class
	 * Singleton-Pattern
	 */
	private static CommandHandler instance = null;

	public static CommandHandler getInstance()
	{
		if (instance == null)
		{
			instance = new CommandHandler();
		}

		return instance;
	}

	/*
	 * Object
	 */
	private HashMap<String, Command> commands = null;

	private CommandHandler()
	{
		commands = new HashMap<String, Command>();
	}

	/**
	 * If a command is written in a Text-Channel of a guild,
	 * then it will be handled here
	 * First it gets checked, if the usage of the command is ok
	 * If it is, it will be executed
	 */
	public void handleCommand(CommandParser cmd) throws Exception
	{
		Logger log = LoggerManager.getLogger(ILogMain.NUM, ILogMain.NAME);

		System.out.println(cmd.getInvoke());
		log.logState(ILogMain.COMMAND_HANLDER, ILogMain.COMMAND);

		if (getCommands().containsKey(cmd.getInvoke()))
		{
			boolean safe = true;
			safe = getCommands().get(cmd.getInvoke()).called(cmd.getArgs(), cmd.getEvent());
			if (safe)
			{
				getCommands().get(cmd.getInvoke()).action(cmd.getArgs(), cmd.getEvent());
				getCommands().get(cmd.getInvoke()).executed(safe, cmd.getEvent());
			}
			else
			{
				getCommands().get(cmd.getInvoke()).executed(safe, cmd.getEvent());
			}
		}
		else
		{
			log.addMessageToLog(ILogMain.COMMAND_HANLDER, LogMessageType.ERROR, ILogMain.COMMAND_UNKNOWN,
					IMainUtil.NOT_IN_CMD_TABLE);
		}
	}

	public HashMap<String, Command> getCommands()
	{
		return commands;
	}

}
