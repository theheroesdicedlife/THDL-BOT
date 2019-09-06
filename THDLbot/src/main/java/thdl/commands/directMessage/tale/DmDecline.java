package thdl.commands.directMessage.tale;


import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import thdl.commands.directMessage.DirectCommand;
import thdl.commands.directMessage.ILogDirectMsg;
import thdl.lib.discord.ThdlMember;
import thdl.lib.factories.discord.ThdlMemberFactory;
import thdl.lib.rpg.Tale;
import thdl.util.DirectWriter;
import thdl.util.DiscordWriter;
import thdl.util.log.LogMessageType;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class DmDecline implements DirectCommand
{

	/*
	 * Is used for all possible accept actions
	 */

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

		thdlMem = ThdlMemberFactory.getInstance().getMember(author);

		if (thdlMem != null)
		{
			isOk = checkDeclinePattern(args);
		}
		else
		{
			log.addMessageToLog(this.toString(), LogMessageType.ERROR, "The user is not a member",
					" With name: " + author.getName());
			isOk = false;
		}

		return isOk;
	}

	public boolean checkDeclinePattern(String[] args)
	{
		boolean isOk = false;
		String returnMsg = "";

		if (args.length > 0)
		{
			switch (args[0])
			{
				case "invite":
				{
					accDecType = AcceptDeclineType.INVITE;

					if (args.length == 2)
					{
						tale = thdlMem.getInvitedTo(args[1]);

						if (tale != null)
						{
							isOk = true;
						}
						else
						{
							returnMsg = "You are not invited to " + args[1];
							log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogDirectMsg.NOT_INVITED,
									"The member " + thdlMem.getMember().getEffectiveName()
											+ " was not invited to the tale " + args[1]);
							dmWriter.writeMsg(returnMsg);
							isOk = false;
						}
					}
					else
					{
						returnMsg = "You need to use the pattern: decline invite [talename]";
						log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogDirectMsg.WRONG_PATTERN,
								"You need to use the pattern: decline invite [talename]");
						dmWriter.writeMsg(returnMsg);
						isOk = false;
					}

					break;
				}
				default:
				{
					log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogDirectMsg.WRONG_PATTERN,
							"Can only accept or decline: invite, trade");
					dmWriter.writeMsg("You can accept or decline: invite, trade");
					isOk = false;
					break;
				}
			}
		}
		else
		{
			log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogDirectMsg.WRONG_PATTERN,
					"Pattern needed: decline [what] ([other arguments])");
			dmWriter.writeMsg("You need to use the pattern: decline [what] ([other arguments])");
			isOk = false;
		}
		return isOk;
	}

	@Override
	public void action(String[] args, PrivateMessageReceivedEvent event) throws Exception
	{
		String msg = "";

		switch (accDecType)
		{
			case INVITE:
			{
				InviteHandler handler = new InviteHandler();

				handler.handleDecline(thdlMem, tale);

				DiscordWriter writer = new DiscordWriter(tale.getMainChannel());

				log.addMessageToLog(this.toString(), LogMessageType.SUCCESS, ILogDirectMsg.PLAYER_DEC,
						"Player didn't join the tale, was removed from invites");

				dmWriter.writeMsg(
						"Such a pity, I thought you would join your friends to play THDL. Well, take care then.");

				msg = "I'm sorry, but this player declined the invite to play THDL: "
						+ thdlMem.getMember().getAsMention();
				writer.writeInfo(msg);

				break;
			}
			default:
			{
				log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogDirectMsg.ACCEPT_TYPE,
						"Unexpected Error with wrong acc-dec-type: " + accDecType);
				dmWriter.writeMsg("An unexpected error as occured. Please contact a bot-Administrator");
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
					ILogDirectMsg.DMC_DECLINE_SUCCESS);
		}
		else
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogDirectMsg.CMD_EXE,
					ILogDirectMsg.DMC_DECLINE_FAIL);
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
