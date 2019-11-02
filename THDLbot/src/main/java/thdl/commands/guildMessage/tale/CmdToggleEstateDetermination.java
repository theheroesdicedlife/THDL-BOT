package thdl.commands.guildMessage.tale;


import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import thdl.commands.guildMessage.Command;
import thdl.commands.guildMessage.ILogGuildCmd;
import thdl.lib.factories.rpg.TaleFactory;
import thdl.lib.rpg.Tale;
import thdl.util.DirectWriter;
import thdl.util.DiscordWriter;
import thdl.util.log.LogMessageType;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class CmdToggleEstateDetermination implements Command
{

	// Command: -toggleDetEstate
	private DiscordWriter	writer		= null;
	private DirectWriter	pmWriter	= null;
	private Logger			log			= null;
	private Tale			tale		= null;

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		boolean isOk = false;

		writer = new DiscordWriter(e);
		log = LoggerManager.getLogger(ILogGuildCmd.NUM, ILogGuildCmd.NAME);

		try
		{
			pmWriter = new DirectWriter(e.getAuthor());
		}
		catch (Exception e1)
		{
			log.logException(this.toString(), ILogGuildCmd.OPEN_DM_CHANNEL, e1.getMessage());
		}
		tale = TaleFactory.getInstance().getTale(e.getChannel());

		if (tale != null)
		{
			if (tale.isStoryteller(e.getAuthor().getId()))
			{
				if (!tale.isStarted() && !tale.isCharacterCreationEnabled())
				{
					if (args.length == 0)
					{
						isOk = true;
					}
					else
					{
						log.logInfo(this.toString(), ILogGuildCmd.WRONG_FORMAT, ILogGuildCmd.WRONG_PATTERN_CMD);
						writer.writeInfo("You should use the format -toggleDetEstate");

						isOk = false;
					}
				}
				else
				{
					log.logInfo(this.toString(), ILogGuildCmd.NO_CHANGE_POSSIBLE,
							"Cannot change tale settings; Either started or enabled character-creation");
					writer.writeInfo(
							"You can't change the tale settings, if started or the character-creation is enabled");

					isOk = false;
				}
			}
			else
			{
				log.logErrorWithoutMsg(this.toString(), ILogGuildCmd.UNAUTHORIZED_USE);
				writer.writeError(ILogGuildCmd.ERROR_NOT_AUTHORIZED_IN_TALE);

				isOk = false;
			}
		}
		else
		{
			log.logErrorWithoutMsg(this.toString(), ILogGuildCmd.NO_TALE_FOUND);
			pmWriter.writeMsg(ILogGuildCmd.ERROR_NOT_A_TALE_CHANNEL);

			e.getMessage().delete().queue();

			isOk = false;
		}

		return isOk;
	}

	@Override
	public void action(String[] args, GuildMessageReceivedEvent e) throws Exception
	{

		tale.toggleEstateDetermination();

		if (tale.isEstateDetermination())
		{
			writer.writeInfo("The determination of the character estate is now randomized");
		}
		else
		{
			writer.writeInfo("Every character will be created with the estate of a simple worker");
		}

	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		if (success)
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_TOGGLE_ESTATE_SUCCESS);
		}
		else
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_TOGGLE_ESTATE_FAILED);
		}

		writer = null;
		pmWriter = null;
		tale = null;
	}

	@Override
	public String help()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
