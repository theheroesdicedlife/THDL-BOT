package thdl.commands.directMessage;


import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import thdl.bot.ILogMain;
import thdl.commands.guildMessage.ILogGuildCmd;
import thdl.lib.discord.ThdlMember;
import thdl.lib.factories.discord.ThdlMemberFactory;
import thdl.util.DiscordWriter;
import thdl.util.log.LogMessageType;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class DmPrintLogs implements DirectCommand
{

	private DiscordWriter	writer	= null;
	private Logger			log		= null;

	@Override
	public boolean called(String[] args, PrivateMessageReceivedEvent event)
	{
		writer = DiscordWriter.createWriter(writer);
		log = LoggerManager.getLogger(ILogDirectMsg.NUM, ILogDirectMsg.NAME);

		ThdlMember member = ThdlMemberFactory.getMember(event.getAuthor());

		boolean isOk = false;

		if (member.isAdmin())
		{
			if (args.length == 0)
			{
				isOk = true;
			}
			else
			{
				writer.writePrivate(IDirectMsgCmd.PATTERN_SHOW_LOG, event.getAuthor());
				log.addMessageToLog(this.toString(), LogMessageType.INFO, ILogDirectMsg.WRONG_PATTERN,
						IDirectMsgCmd.PATTERN_SHOW_LOG);
			}
		}
		else
		{
			writer.writePrivate(IDirectMsgCmd.NO_PERMISSION, event.getAuthor());
			log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogDirectMsg.NO_PERMISSION,
					IDirectMsgCmd.NO_PERMISSION);
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
		writer.writePrivate(IDirectMsgCmd.SHOW_LOG, event.getAuthor());
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
