package thdl.commands.directMessage;


import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import thdl.bot.ILogMain;
import thdl.commands.guildMessage.ILogGuildCmd;
import thdl.lib.discord.ThdlMember;
import thdl.lib.factories.discord.ThdlMemberFactory;
import thdl.util.DirectWriter;
import thdl.util.log.LogMessageType;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class DmPrintLogs implements DirectCommand
{

	private DirectWriter	writer	= null;
	private Logger			log		= null;

	@Override
	public boolean called(String[] args, PrivateMessageReceivedEvent event)
	{

		log = LoggerManager.getLogger(ILogDirectMsg.NUM, ILogDirectMsg.NAME);
		ThdlMember member = ThdlMemberFactory.getInstance().getMember(event.getAuthor());
		try
		{
			writer = new DirectWriter(member);
		}
		catch (Exception e)
		{
			log.logException(this.toString(), ILogDirectMsg.OPEN_DM_CHANNEL, e.getMessage());
		}

		boolean isOk = false;

		if (member.isAdmin())
		{
			if (args.length == 0)
			{
				isOk = true;
			}
			else
			{
				writer.writeMsg(IDirectMsgCmd.PATTERN_SHOW_LOG);
				log.logInfo(this.toString(), ILogDirectMsg.WRONG_PATTERN, IDirectMsgCmd.PATTERN_SHOW_LOG);
			}
		}
		else
		{
			writer.writeMsg(IDirectMsgCmd.NO_PERMISSION);
			log.logErrorWithoutMsg(this.toString(), ILogDirectMsg.NO_PERMISSION);
		}
		return isOk;
	}

	@Override
	public void action(String[] args, PrivateMessageReceivedEvent event) throws Exception
	{
		Logger mainLog = LoggerManager.getLogger(ILogMain.NUM, ILogMain.NAME);
		mainLog.getLog().printLog();
		Logger cmdLog = LoggerManager.getLogger(ILogGuildCmd.NUM, ILogGuildCmd.NAME);
		cmdLog.getLog().printLog();
		Logger dmLog = LoggerManager.getLogger(ILogDirectMsg.NUM, ILogDirectMsg.NAME);
		dmLog.getLog().printLog();
		writer.writeMsg(IDirectMsgCmd.SHOW_LOG);
		log.addMessageToLog(this.toString(), LogMessageType.INFO, ILogDirectMsg.SHOW_LOGS, IDirectMsgCmd.SHOW_LOG);
	}

	@Override
	public void executed(boolean success, PrivateMessageReceivedEvent event)
	{
		if (success)
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogDirectMsg.CMD_EXE,
					ILogDirectMsg.DMC_PRINTLOGS_SUCCESS);
		}
		else
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogDirectMsg.CMD_EXE,
					ILogDirectMsg.DMC_PRINTLOGS_FAIL);
		}
		writer = null;
		log = null;
	}

	@Override
	public String help()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
