package thdl.commands.directMessage.tale;


import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.managers.GuildController;
import thdl.commands.directMessage.DirectCommand;
import thdl.commands.directMessage.IDirectMsgCmd;
import thdl.commands.directMessage.ILogDirectMsg;
import thdl.lib.discord.ThdlMember;
import thdl.lib.factories.discord.ThdlMemberFactory;
import thdl.lib.rpg.Tale;
import thdl.util.DirectWriter;
import thdl.util.DiscordWriter;
import thdl.util.IDiscordID;
import thdl.util.log.LogMessageType;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class DmAccept implements DirectCommand
{

	/*
	 * Command: accept invite [talename]
	 */

	private DirectWriter		dmWriter	= null;
	private Logger				log			= null;
	private ThdlMember			thdlMem		= null;
	private AcceptDeclineType	accDecType	= null;
	private Tale				tale		= null;

	@Override
	public boolean called(String[] args, PrivateMessageReceivedEvent event)
	{
		boolean isOk = false;
		User author = event.getAuthor();
		log = LoggerManager.getLogger(ILogDirectMsg.NUM, ILogDirectMsg.NAME);

		try
		{
			dmWriter = new DirectWriter(author);
		}
		catch (Exception e)
		{
			log.logException(this.toString(), ILogDirectMsg.OPEN_DM_CHANNEL, e.getMessage());
			isOk = false;
		}

		thdlMem = ThdlMemberFactory.getMember(author);

		if (thdlMem != null)
		{
			isOk = checkAcceptPattern(args);
		}
		else
		{
			log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogDirectMsg.USER_IS_NO_MEMBER,
					IDirectMsgCmd.NOT_A_MEMBER);
			isOk = false;
		}

		return isOk;
	}

	public boolean checkAcceptPattern(String[] args)
	{
		boolean isOk = false;
		String returnMsg = "";

		if (args.length > 0)
		{
			switch (args[0])
			{
				case IDirectMsgCmd.INVITE:
				{
					accDecType = AcceptDeclineType.INVITE;

					if (args.length == 2)
					{
						tale = thdlMem.getInvitedTo(args[1]);

						if (tale != null)
						{
							if (!tale.isMemberAlreadyInTale(thdlMem))
							{
								if (!tale.isStarted())
								{
									isOk = true;
								}
								else
								{
									log.addMessageToLog(this.toString(), LogMessageType.ERROR,
											ILogDirectMsg.TALE_STARTED, IDirectMsgCmd.TALE_STARTED);
									dmWriter.writeMsg(IDirectMsgCmd.TALE_STARTED);
									isOk = false;
								}
							}
							else
							{
								returnMsg = IDirectMsgCmd.ALREADY_PLAYER + tale.getTaleName();
								log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogDirectMsg.ALREADY_PLAYER,
										returnMsg);
								dmWriter.writeMsg(returnMsg);
								isOk = false;
							}
						}
						else
						{
							returnMsg = IDirectMsgCmd.NOT_INVITED + args[1];
							log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogDirectMsg.NOT_INVITED,
									returnMsg);
							dmWriter.writeMsg(returnMsg);
							isOk = false;
						}
					}
					else
					{
						returnMsg = IDirectMsgCmd.PATTERN_ACC_SPEC + IDirectMsgCmd.PATTERN_ACC_DEC_INV;
						log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogDirectMsg.WRONG_PATTERN,
								returnMsg);
						dmWriter.writeMsg(returnMsg);
						isOk = false;
					}

					break;
				}
				default:
				{
					log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogDirectMsg.WRONG_PATTERN,
							IDirectMsgCmd.ACC_DEC_TYPES);
					dmWriter.writeMsg(IDirectMsgCmd.ACC_DEC_TYPES);
					isOk = false;
					break;
				}
			}
		}
		else
		{
			log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogDirectMsg.WRONG_PATTERN,
					IDirectMsgCmd.PATTERN_ACCEPT);
			dmWriter.writeMsg(IDirectMsgCmd.PATTERN_ACCEPT);
			isOk = false;
		}
		return isOk;
	}

	@Override
	public void action(String[] args, PrivateMessageReceivedEvent event) throws Exception
	{
		GuildController gc = event.getJDA().getGuildById(IDiscordID.GUILD_ID).getController();
		String msg = "";

		switch (accDecType)
		{
			case INVITE:
			{
				/**
				 * Accepted Invites for joining a tale
				 */
				InviteHandler handler = new InviteHandler();
				handler.handleAccept(thdlMem, tale, gc);

				DiscordWriter writer = new DiscordWriter(tale.getMainChannel());

				msg = msg + IDirectMsgCmd.PLAYER_ADD_TO_TALE + tale.getTaleName();

				log.addMessageToLog(this.toString(), LogMessageType.SUCCESS, ILogDirectMsg.PLAYER_ADD, msg);

				msg = msg + IDirectMsgCmd.NEXT_LINE + IDirectMsgCmd.SAY_HALLO + tale.getMainChannel().getAsMention();
				msg = msg + IDirectMsgCmd.NEXT_LINE + IDirectMsgCmd.START_WITH_CREATION;
				msg = msg + IDirectMsgCmd.NEXT_LINE + IDirectMsgCmd.WE_WILL_MEET;

				dmWriter.writeMsg(msg);
				msg = "";

				msg = IDirectMsgCmd.WELCOME + thdlMem.getMember().getAsMention();
				writer.writeSuccess(msg);

				break;
			}
			default:
			{
				log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogDirectMsg.ACCEPT_TYPE,
						IDirectMsgCmd.UNEXPECTED_ERROR);
				dmWriter.writeMsg(IDirectMsgCmd.UNEXPECTED_ERROR);
				break;
			}
		}

	}

	@Override
	public void executed(boolean success, PrivateMessageReceivedEvent event)
	{
		if (success)
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogDirectMsg.CMD_EXE,
					ILogDirectMsg.DMC_ACCEPT_SUCCESS);
		}
		else
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogDirectMsg.CMD_EXE,
					ILogDirectMsg.DMC_ACCEPT_FAIL);
		}

		dmWriter = null;
		log = null;
		thdlMem = null;
		accDecType = null;
		tale = null;
	}

	@Override
	public String help()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
