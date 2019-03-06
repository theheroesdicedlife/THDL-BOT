package thdl.commands.guildMessage.tale;


import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import thdl.commands.guildMessage.Command;
import thdl.commands.guildMessage.IGuildMsgCmd;
import thdl.commands.guildMessage.ILogGuildCmd;
import thdl.lib.factories.rpg.RaceFactory;
import thdl.lib.factories.rpg.TaleFactory;
import thdl.lib.rpg.Tale;
import thdl.util.DirectWriter;
import thdl.util.DiscordWriter;
import thdl.util.log.LogMessageType;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class CmdAddMoreRaces implements Command
{

	private DiscordWriter	writer	= null;
	private DirectWriter	pmWrite	= null;
	private Logger			log		= null;
	private Tale			tale	= null;

	/**
	 * -addRace [racename] ([racename] ...)
	 */

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		writer = new DiscordWriter(e);
		try
		{
			pmWrite = new DirectWriter(e.getAuthor());
		}
		catch (Exception e1)
		{
			log.addMessageToLog(this.toString(), LogMessageType.EXCEPTION, ILogGuildCmd.OPEN_DM_CHANNEL,
					e1.getMessage());
		}
		log = LoggerManager.getLogger(ILogGuildCmd.NUM, ILogGuildCmd.NAME);

		boolean isOK = false;

		tale = TaleFactory.getTale(e.getChannel());

		if (tale != null)
		{
			if (tale.isStoryteller(e.getAuthor().getId()))
			{
				if (!tale.hasPlayer())
				{
					if (args.length > 0)
					{
						if (RaceFactory.areRaces(args))
						{
							isOK = true;
						}
						else
						{
							log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogGuildCmd.NOT_A_THDL_RACE,
									IGuildMsgCmd.ERROR_ONE_RACE_NAME_FALSE);
							writer.writeError(IGuildMsgCmd.ERROR_ONE_RACE_NAME_FALSE);
							writer.writeInfo(IGuildMsgCmd.INFO_STANDARD_RACE_NAMES);
							writer.writeInfo(IGuildMsgCmd.INFO_ADVANCED_RACE_NAMES);

							isOK = false;
						}
					}
					else
					{
						log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogGuildCmd.WRONG_FORMAT,
								IGuildMsgCmd.INFO_FORMAT_ADD_ROLES);
						writer.writeInfo(IGuildMsgCmd.INFO_FORMAT_ADD_ROLES);

						isOK = false;
					}
				}
				else
				{
					log.addMessageToLog(this.toString(), LogMessageType.INFO, ILogGuildCmd.ALREADY_PLAYERS_JOINED,
							IGuildMsgCmd.INFO_PLAYERS_HAVE_JOINED);
					writer.writeInfo(IGuildMsgCmd.INFO_PLAYERS_HAVE_JOINED);

					isOK = false;
				}
			}
			else
			{
				log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogGuildCmd.UNAUTHORIZED_USE,
						IGuildMsgCmd.ERROR_NOT_AUTHORIZED_IN_TALE);
				writer.writeError(IGuildMsgCmd.ERROR_NOT_AUTHORIZED_IN_TALE);

				isOK = false;
			}
		}
		else
		{
			log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogGuildCmd.NO_TALE_FOUND,
					IGuildMsgCmd.ERROR_NOT_A_TALE_CHANNEL);
			pmWrite.writeMsg(IGuildMsgCmd.ERROR_NOT_A_TALE_CHANNEL);

			e.getMessage().delete().queue();

			isOK = false;
		}

		return isOK;
	}

	@Override
	public void action(String[] args, GuildMessageReceivedEvent e) throws Exception
	{
		addRacesToTale(args);
	}

	private void addRacesToTale(String[] names)
	{
		for (String name : names)
		{
			if (!tale.isRaceInTale(name))
			{
				tale.addRace(name);
			}
		}
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		if (success)
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_ADD_RACE_SUCCESS);
		}
		else
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_ADD_RACE_FAILED);
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
