package thdl.commands.guildMessage.tale;


import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import thdl.commands.guildMessage.Command;
import thdl.commands.guildMessage.ILogGuildCmd;
import thdl.lib.discord.ThdlMember;
import thdl.lib.factories.discord.ThdlMemberFactory;
import thdl.lib.factories.rpg.TaleFactory;
import thdl.util.DirectWriter;
import thdl.util.DiscordWriter;
import thdl.util.log.LogMessageType;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class CmdCreateNewTale implements Command
{

	private DiscordWriter	writer		= null;
	private DirectWriter	dmWriter	= null;
	private Logger			log			= null;

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		boolean isOk = false;
		String talename = "";
		log = LoggerManager.getLogger(ILogGuildCmd.NUM, ILogGuildCmd.NAME);
		writer = new DiscordWriter(e);
		try
		{
			dmWriter = new DirectWriter(e.getAuthor());
		}
		catch (Exception e1)
		{
			log.logException(this.toString(), ILogGuildCmd.OPEN_DM_CHANNEL, e1.getMessage());

		}

		ThdlMember member = ThdlMemberFactory.getInstance().getMember(e.getAuthor());

		if (member != null)
		{
			if (member.isDungeonmaster())
			{
				if (args.length == 1)
				{
					talename = args[0];
					if (!TaleFactory.getInstance().isNameInUse(talename))
					{
						isOk = true;
					}
					else
					{
						isOk = false;
						log.logInfo(this.toString(), ILogGuildCmd.NAME_IN_USE,
								"Name which was chosen is already in use!");
						writer.writeInfo("I'm sorry. The name for your tale is already in use");
					}
				}
				else
				{
					isOk = false;
					log.logInfo(this.toString(), ILogGuildCmd.WRONG_FORMAT, ILogGuildCmd.WRONG_PATTERN_CMD);
					writer.writeInfo("You should use the format -createTale [talename]");
				}
			}
			else
			{
				isOk = false;

				log.logErrorWithoutMsg(this.toString(), ILogGuildCmd.UNAUTHORIZED_USE);
				dmWriter.writeMsg("You are not a Storyteller");
			}
		}
		else
		{
			isOk = false;
			String error = e.getGuild().getOwner().getAsMention() + " is not registered as member of the guild";
			log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogGuildCmd.NO_MEMBER,
					"The Author of this command is not registered");
			writer.writeError(error);
		}

		return isOk;
	}

	@Override
	public void action(String[] args, GuildMessageReceivedEvent e)
	{
		TaleCreationHandler creationHandler = new TaleCreationHandler();

		try
		{
			if (creationHandler.createNewTale(args[0], e, log))
			{
				writer.writeSuccess("The THDL-based PnP " + args[0] + " was created!");
			}
		}
		catch (Exception e1)
		{
			log.logException(this.toString(), ILogGuildCmd.CREATE_TALE_EXCEPTION, e1.getMessage());
		}
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		if (success)
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_CREATE_PNP_SUCCESS);
		}
		else
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_CREATE_PNP_FAILED);
		}

		writer = null;
		dmWriter = null;
	}

	@Override
	public String help()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
