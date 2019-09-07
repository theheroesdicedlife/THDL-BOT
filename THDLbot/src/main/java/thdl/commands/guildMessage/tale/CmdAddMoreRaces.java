package thdl.commands.guildMessage.tale;


import java.sql.SQLException;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import thdl.commands.guildMessage.Command;
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
							writer.writeError("At least one of the race-names used, was not a THDL-Racename");
							writer.writeInfo(
									"The names of the standard THDL-Races are: Human, Elf, Dwarf, Orc, Halfelf, Str-Type-Demi, Psy-Type-Demi, Dex-Type-Demi");
							writer.writeInfo(
									"The names of the advanced set of THDL-Races are: Oger, Gnome, Fallen Angel, Angel, Oni, Humanoid Slime, Liszardman, Goblin");

							isOK = false;
						}
					}
					else
					{
						log.logInfo(this.toString(), ILogGuildCmd.WRONG_FORMAT, ILogGuildCmd.WRONG_PATTERN_CMD);
						writer.writeInfo("You should use the format -addRace [racename] ([racename] ...)");

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
		addRacesToTale(args);
	}

	private void addRacesToTale(String[] names)
	{
		StringBuilder msg = new StringBuilder();
		msg.append("The following races were added to the pnp " + tale.getTaleName() + "\n");

		for (String name : names)
		{
			if (!tale.isRaceInTale(name))
			{
				msg.append(name + "\n");
				tale.addRace(name);

			}
		}

		writer.writeInfo(msg.toString());

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
