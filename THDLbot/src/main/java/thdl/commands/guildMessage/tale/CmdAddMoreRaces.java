package thdl.commands.guildMessage.tale;


import java.sql.SQLException;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import thdl.commands.guildMessage.Command;
import thdl.commands.guildMessage.IGuildMsgCmd;
import thdl.commands.guildMessage.ILogGuildCmd;
import thdl.lib.factories.rpg.RaceFactory;
import thdl.lib.factories.rpg.TaleFactory;
import thdl.lib.rpg.Tale;
import thdl.update.rpg.TaleUpdate;
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
		log = LoggerManager.getLogger(ILogGuildCmd.NUM, ILogGuildCmd.NAME);
		try
		{
			pmWrite = new DirectWriter(e.getAuthor());
		}
		catch (Exception e1)
		{
			log.logException(this.toString(), ILogGuildCmd.OPEN_DM_CHANNEL, e1.getMessage());
		}

		boolean isOK = false;

		tale = TaleFactory.getInstance().getTale(e.getChannel());

		if (tale != null)
		{
			if (tale.isStoryteller(e.getAuthor().getId()))
			{
				if (!tale.hasPlayer())
				{
					if (args.length > 0)
					{
						if (RaceFactory.getInstance().areRaces(args))
						{
							isOK = true;
						}
						else
						{
							log.logErrorWithoutMsg(this.toString(), ILogGuildCmd.NOT_A_THDL_RACE);
							writer.writeError(IGuildMsgCmd.ERROR_ONE_RACE_NAME_FALSE);
							writer.writeInfo(IGuildMsgCmd.INFO_STANDARD_RACE_NAMES);
							writer.writeInfo(IGuildMsgCmd.INFO_ADVANCED_RACE_NAMES);

							isOK = false;
						}
					}
					else
					{
						log.logInfo(this.toString(), ILogGuildCmd.WRONG_FORMAT, IGuildMsgCmd.INFO_FORMAT_ADD_RACE);
						writer.writeInfo(IGuildMsgCmd.INFO_FORMAT_ADD_RACE);

						isOK = false;
					}
				}
				else
				{
					log.logInfo(this.toString(), ILogGuildCmd.ALREADY_PLAYERS_JOINED,
							IGuildMsgCmd.INFO_PLAYERS_HAVE_JOINED);
					writer.writeInfo(IGuildMsgCmd.INFO_PLAYERS_HAVE_JOINED);

					isOK = false;
				}
			}
			else
			{
				log.logErrorWithoutMsg(this.toString(), ILogGuildCmd.UNAUTHORIZED_USE);
				writer.writeError(IGuildMsgCmd.ERROR_NOT_AUTHORIZED_IN_TALE);

				isOK = false;
			}
		}
		else
		{
			log.logErrorWithoutMsg(this.toString(), ILogGuildCmd.NO_TALE_FOUND);
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
		StringBuilder msg = new StringBuilder();
		msg.append(IGuildMsgCmd.INFO_RACES_ADDED + tale.getTaleName() + IGuildMsgCmd.NEXT_LINE);

		for (String name : names)
		{
			if (!tale.isRaceInTale(name))
			{
				msg.append(name);
				tale.addRace(name);

				writer.writeInfo(msg.toString());
			}
		}

		try
		{
			TaleUpdate.getInstance().updateTaleRaces(tale);
		}
		catch (SQLException e)
		{
			log.logException(this.toString(), ILogGuildCmd.DB_UPDATE_FAILED, e.getMessage());
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
