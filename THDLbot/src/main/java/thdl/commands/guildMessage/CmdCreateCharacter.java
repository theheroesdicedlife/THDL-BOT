package thdl.commands.guildMessage;


import javax.xml.stream.events.Characters;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import thdl.factories.CharacterCreator;
import thdl.rpg.lib.Jobs;
import thdl.rpg.lib.Player;
import thdl.rpg.lib.PlayerCharacter;
import thdl.rpg.lib.Tale;
import thdl.rpg.lib.Tales;
import thdl.util.DiscordWriter;
import thdl.util.ErrorMessages;


public class CmdCreateCharacter implements Command
{

	/**
	 * TODO: Rewind of the class
	 */
	private DiscordWriter	writer		= null;
	private Player			thePlayer	= null;
	private Tale			tale		= null;
	private String			name		= "";
	private String			race		= "";
	private String			job			= "";

	/**
	 * createCharacter [name] [race] ([job])
	 * Character per tale
	 * In DB saved by an ID of tale + charactername
	 */

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		writer = openWriter(e);
		boolean isOk = false;
		tale = Tales.getTale(e.getChannel());

		if (tale != null)
		{
			if (!tale.isThisMyStoryTeller(e.getAuthor()))
			{
				thePlayer = tale.getPlayer(e.getMember());
				if (!tale.getIsStarted())
				{
					if (thePlayer.getMyCharacter() == null)
					{

						if (args.length > 1)
						{
							name = args[0];
							race = args[1];

							if (!tale.isCharacterNameInUse(name))
							{

								if (tale.getPlayableRace(race))
								{
									if (!tale.isAreJobsEnabled())
									{
										if (args.length == 2)
										{
											isOk = true;
										}
										else
										{
											System.out.println(ErrorMessages.FORMAT_ERROR);
											writer.writeInfo("Jobs are disabled");
											writer.writeInfo(
													"You should use the layout -createCharacter [name] [race]");
										}
									}
									else
									{
										if (args.length == 3)
										{
											job = args[2];
											if (Jobs.isThisAJob(job))
											{
												isOk = true;
											}
											else
											{
												System.out.println("Not a job of THDL");
												writer.writeError("This not a job in THDL");
											}
										}
										else
										{
											System.out.println(ErrorMessages.FORMAT_ERROR);
											writer.writeInfo("Jobs are enabled");
											writer.writeInfo(
													"You should use the layout -createCharacter [name] [race] [job]");
										}
									}
								}
								else
								{
									System.out.println("Race is not playable for this tale");
									writer.writeError("This race is not playable in the tale you are playing");
								}
							}
							else
							{
								System.out.println("Charactername in use");
								writer.writeError("Your chosen name is already in use");
							}
						}
						else
						{
							System.out.println(ErrorMessages.FORMAT_ERROR);
							writer.writeInfo("You should give your character a name and a race");
						}
					}
					else
					{
						System.out.println("Player already has a character");
						writer.writeError("You cannot have more than one character");
					}
				}
				else
				{
					System.out.println(ErrorMessages.STARTED_ERROR);
					writer.writeInfo("I'm sorry, but the tale was already started");
				}
			}
			else
			{
				System.out.println(ErrorMessages.TELLER_OF_TALE_ERROR);
				writer.writeError("You don't need a playable Character in the tale you are telling");
			}
		}
		else
		{
			System.out.println(ErrorMessages.CHANNEL_ERROR);
			writer.writePrivate("Not the channel of your Tale", e.getAuthor());
		}

		return isOk;
	}

	@Override
	public void action(String[] args, GuildMessageReceivedEvent e) throws Exception
	{
		CharacterCreator cc = new CharacterCreator(name, race, job, e, thePlayer, tale);
		PlayerCharacter chara = cc.createCharacter(writer);

		tale.addCharacter(chara.getName(), thePlayer);
		thePlayer.setMyCharacter(chara);
		Characters.addCharacter(chara);

		thePlayer.safePlayer();

		writer.writeSuccess(
				e.getMember().getAsMention() + " your Character " + chara.getName() + " is ready to get played!");
		writer.writeInfo("Its race is " + chara.getMyRace() + " and it is " + chara.getAge() + " years old");

		if (tale.isAreJobsEnabled())
		{
			writer.writeInfo("Your first job is " + chara.getMyJob());
		}
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
		tale = null;
		name = "";
		race = "";
		job = "";
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
