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


public class CmdToggleStartjobs implements Command
{

	// Command: -toggleStartJobs

	private DiscordWriter	writer	= null;
	private DirectWriter	pmWrite	= null;
	private Logger			log		= null;
	private Tale			tale	= null;

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		boolean isOK = false;

		writer = new DiscordWriter(e);
		log = LoggerManager.getLogger(ILogGuildCmd.NUM, ILogGuildCmd.NAME);

		try
		{
			pmWrite = new DirectWriter(e.getAuthor());
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
				if (!tale.hasPlayer())
				{
					if (args.length == 0)
					{
						isOK = true;
					}
					else
					{
						log.logInfo(this.toString(), ILogGuildCmd.WRONG_FORMAT, ILogGuildCmd.WRONG_PATTERN_CMD);
						writer.writeInfo("You should use the format -toggleStartJobs");

						isOK = false;
					}
				}
				else
				{
					log.logInfo(this.toString(), ILogGuildCmd.ALREADY_PLAYERS_JOINED,
							"Cannot change tale settings after player join");
					writer.writeInfo("You can't change the tale settings after a player has joined");

					isOK = false;
				}
			}
			else
			{
				log.logErrorWithoutMsg(this.toString(), ILogGuildCmd.UNAUTHORIZED_USE);
				writer.writeError(ILogGuildCmd.ERROR_NOT_AUTHORIZED_IN_TALE);

				isOK = false;
			}
		}
		else
		{
			log.logErrorWithoutMsg(this.toString(), ILogGuildCmd.NO_TALE_FOUND);
			pmWrite.writeMsg(ILogGuildCmd.ERROR_NOT_A_TALE_CHANNEL);

			e.getMessage().delete().queue();

			isOK = false;
		}

		return isOK;
	}

	@Override
	public void action(String[] args, GuildMessageReceivedEvent e) throws Exception
	{
		tale.toggleStartJobs();

		if (tale.isStartJobsEnabled())
		{
			writer.writeInfo("The start-jobs were enabled. And can be used for the character creation");
		}
		else
		{
			writer.writeInfo("The start-jobs were disabled. And can be used for the character creation");
		}

	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{

		if (success)
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_TOGGLE_JOBS_SUCCESS);
		}
		else
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_TOGGLE_JOBS_FAILED);
		}

		writer = null;
		pmWrite = null;
		tale = null;
	}

	@Override
	public String help()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
