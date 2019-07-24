package thdl.commands.guildMessage.tale;


import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import thdl.commands.guildMessage.Command;
import thdl.commands.guildMessage.IGuildMsgCmd;
import thdl.commands.guildMessage.ILogGuildCmd;
import thdl.lib.discord.ThdlMember;
import thdl.lib.factories.discord.ThdlMemberFactory;
import thdl.lib.factories.rpg.TaleFactory;
import thdl.lib.rpg.Tale;
import thdl.util.DirectWriter;
import thdl.util.DiscordWriter;
import thdl.util.IdParser;
import thdl.util.log.LogMessageType;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class CmdInvitePlayerToTale implements Command
{

	private Logger			log		= null;
	private DiscordWriter	writer	= null;
	private Tale			tale	= null;

	/**
	 * -invite [Ping User] ([Ping User] ...)
	 */

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		log = LoggerManager.getLogger(ILogGuildCmd.NUM, ILogGuildCmd.NAME);
		writer = new DiscordWriter(e);

		boolean isOk = false;
		String authorId = e.getAuthor().getId();
		DirectWriter authorWrite = null;

		tale = TaleFactory.getInstance().getTale(e.getChannel());

		try
		{
			authorWrite = new DirectWriter(e.getAuthor());
		}
		catch (Exception e1)
		{
			log.logException(this.toString(), ILogGuildCmd.OPEN_DM_CHANNEL, e1.getMessage());
		}

		if (tale != null)
		{
			if (tale.isStoryteller(authorId))
			{
				if (!tale.isStarted())
				{
					if (args.length >= 1)
					{
						isOk = true;
					}
					else
					{
						log.logInfo(this.toString(), ILogGuildCmd.WRONG_FORMAT,
								IGuildMsgCmd.INFO_FORMAT_INVITE_PLAYERS);
						writer.writeInfo(IGuildMsgCmd.INFO_FORMAT_INVITE_PLAYERS);
						isOk = false;
					}
				}
				else
				{
					log.logInfo(this.toString(), ILogGuildCmd.TALE_STARTED, IGuildMsgCmd.INFO_TALE_IS_RUNNING);
					writer.writeInfo(IGuildMsgCmd.INFO_TALE_IS_RUNNING);
					isOk = false;
				}

			}
			else
			{
				log.logErrorWithoutMsg(this.toString(), ILogGuildCmd.UNAUTHORIZED_USE);
				writer.writeError(IGuildMsgCmd.ERROR_NOT_AUTHORIZED_IN_TALE);
				isOk = false;
			}
		}
		else
		{
			log.logErrorWithoutMsg(this.toString(), ILogGuildCmd.NO_TALE_FOUND);
			authorWrite.writeMsg(IGuildMsgCmd.ERROR_NOT_A_TALE_CHANNEL);
			isOk = false;
		}

		return isOk;
	}

	/**
	 * TODO: SAVE
	 */
	@Override
	public void action(String[] args, GuildMessageReceivedEvent e) throws Exception
	{
		String logMsg = "";
		String msg = "";

		for (int i = 0; i < args.length; i++)
		{
			String id = IdParser.parse(args[i]);
			ThdlMember member = ThdlMemberFactory.getInstance().getMember(id);

			if (member != null)
			{
				if (member.isAllowed())
				{

					if (!member.isInvitedTo(tale))
					{
						String dmMsg = "";
						member.addInvitedTo(tale);
						DirectWriter dm = new DirectWriter(member);

						dmMsg = IGuildMsgCmd.DM_INV_TO + tale.getTaleName() + IGuildMsgCmd.NEXT_LINE;
						dmMsg = IGuildMsgCmd.DM_ANSW_INV + IGuildMsgCmd.NEXT_LINE;
						dmMsg = IGuildMsgCmd.DM_ACCEPT_INV + IGuildMsgCmd.DM_DECLINE_INV;

						dm.writeMsg(dmMsg);

						msg = msg + args[i] + IGuildMsgCmd.INFO_INVITED + IGuildMsgCmd.NEXT_LINE;

						logMsg = args[i] + ILogGuildCmd.INVITED + tale.getTaleName();
						log.logState(this.toString(), logMsg);
					}
				}
				else
				{
					logMsg = args[i] + ILogGuildCmd.CANT_PLAY;
					log.logErrorWithoutMsg(this.toString(), logMsg);

					msg = msg + args[i] + IGuildMsgCmd.INFO_COULD_NOT_INVITE + IGuildMsgCmd.INFO_NO_INV_CAUSE_NOT_AUTH
							+ IGuildMsgCmd.NEXT_LINE;
				}
			}
			else
			{
				logMsg = args[i] + ILogGuildCmd.NOT_A_MEMBER;
				log.logErrorWithoutMsg(this.toString(), logMsg);

				msg = msg + args[i] + IGuildMsgCmd.INFO_COULD_NOT_INVITE + IGuildMsgCmd.INFO_NO_INV_CAUSE_NO_MEMBER
						+ IGuildMsgCmd.NEXT_LINE;
			}
		}

		writer.writeInfo(msg);
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		if (success)
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_INVITE_SUCCESS);
		}
		else
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_INVITE_FAILED);
		}

		writer = null;
		log = null;
		tale = null;
	}

	@Override
	public String help()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
