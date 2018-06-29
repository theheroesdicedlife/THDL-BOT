package commands;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import rpglib.Characters;
import rpglib.Job;
import rpglib.JobTemplate;
import rpglib.Player;
import rpglib.PlayerCharacter;
import rpglib.Race;
import rpglib.RaceTemplate;
import rpglib.Tale;
import rpglib.Tales;
import thdl.bot.DiscordWriter;
import util.ErrorMessages;

public class CmdCreateCharacter implements Command
{

	private DiscordWriter	writer		= null;
	private Player			thePlayer	= null;
	private Race			race		= null;
	private Job				job			= null;
	private int				ages		= 3;

	/**
	 * Character per tale
	 * In DB saved by an ID of tale + charactername
	 */

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		writer = openWriter(e);
		boolean isOk = false;
		Tale tale = Tales.getTale(e.getChannel());

		if (tale != null)
		{
			if (args.length == 4)
			{
				String name = args[0];
				String ageTest = args[1];
				String raceName = args[2];
				String jobName = args[3];

				if (!tale.getIsStarted())
				{
					if (tale.isPlayerInTale(e.getMember()))
					{
						thePlayer = tale.getPlayer(e.getMember());
						if (thePlayer.getMyCharacter() == null)
						{
							if (!tale.isNameInUse(name))
							{
								race = RaceTemplate.getPlayableRace(raceName);
								job = JobTemplate.getStartJob(jobName);

								if (race != null)
								{
									ages = race.isAgeOk(ageTest);
									if (ages != 3)
									{
										if (job != null)
										{
											isOk = true;
										}
										else
										{
											System.out.println("Job is not a startjob");
											writer.writeError("The class is not a startclass");
										}
									}
									else
									{
										System.out.println("Not a legal age");
										writer.writeError("This age is not a legal age");
									}
								}
								else
								{
									System.out.println("Race is not playable");
									writer.writeError("The race you named is not playable");
								}
							}
							else
							{
								System.out.println("Name already is in use");
								writer.writeError("Sorry! This name is already in use");
							}
						}
						else
						{
							System.out.println("Player has a character in this tale");
							writer.writeError("You already have a Character in this tale");
						}
					}
					else
					{
						System.out.println("The author is not in this tale");
					}
				}
				else
				{
					System.out.println("To late the tale is started");
				}
			}
			else
			{
				System.out.println(ErrorMessages.FORMATERROR);
				writer.writeInfo("You should use the layout -createCharacter [name] [age] [race] [job]");
			}
		}
		else
		{
			System.out.println(ErrorMessages.CHANNELERROR);
		}

		return isOk;
	}

	@Override
	public void action(String[] args, GuildMessageReceivedEvent e)
	{
		int age = 0;

		try
		{
			age = Integer.parseInt(args[1]);
		}
		catch (Exception ex)
		{

		}

		PlayerCharacter chara = new PlayerCharacter(args[0], age, thePlayer);

		chara.addJob(job);
		chara.setRace(race);

		chara.calculateStats(ages, writer);
		chara.createAbilitySet(writer);
		chara.createInventory(writer);

		Characters.addCharacter(chara);
		thePlayer.setMyCharacter(chara);

		writer.writeSuccess("Your character " + chara.getName() + " was created");
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		if (success)
		{
			System.out.println("The command createCharacter was executed with success");
		}
		else
		{
			System.out.println("The command createCharacter could not be executed with success");
		}

		writer = null;
		thePlayer = null;
		race = null;
		job = null;
		ages = 3;
	}

	@Override
	public String help()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiscordWriter openWriter(GuildMessageReceivedEvent e)
	{
		if (writer == null)
		{
			writer = new DiscordWriter(e);
		}
		return writer;
	}

}
