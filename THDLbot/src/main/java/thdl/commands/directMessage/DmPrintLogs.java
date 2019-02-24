package thdl.commands.directMessage;


import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import thdl.bot.ILogMain;
import thdl.commands.guildMessage.ILogGuildCmd;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class DmPrintLogs implements DirectCommand
{

	@Override
	public boolean called(String[] args, PrivateMessageReceivedEvent event)
	{
		boolean isOk = true;
		return isOk;
	}

	@Override
	public void action(String[] args, PrivateMessageReceivedEvent event) throws Exception
	{
		Logger mainLog = LoggerManager.getLogger(ILogMain.NUM, ILogMain.NAME);
		mainLog.getLog().printLog();
		Logger cmdLog = LoggerManager.getLogger(ILogGuildCmd.NUM, ILogGuildCmd.NAME);
		cmdLog.getLog().printLog();
		event.getChannel().sendMessage("I give you the log").queue();
	}

	@Override
	public void executed(boolean success, PrivateMessageReceivedEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String help()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
