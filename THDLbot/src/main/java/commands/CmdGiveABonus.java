package commands;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import rpglib.Player;
import rpglib.PlayerCharacter;
import rpglib.Stats;
import rpglib.Tale;
import rpglib.Tales;
import rpglib.Turn;
import thdl.bot.DiscordWriter;
import util.ErrorMessages;

public class CmdGiveABonus implements Command, Stats
{

	DiscordWriter	writer	= null;
	int				value	= 0;
	int				time	= 0;
	PlayerCharacter	aPC		= null;
	Turn			aTurn	= null;

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		writer = openWriter(e);
		boolean isOk = false;
		Tale aTale = Tales.getTale(e.getChannel());

		if (aTale != null)
		{
			String storytellerId = aTale.getStoryteller().getUser().getId();
			String authorId = e.getAuthor().getId();
			if (storytellerId.equals(authorId))
			{
				if (aTale.getIsStarted())
				{
					// if (aTale.isInBattle)
					// {
					if (args.length == 5)
					{
						if (aTale.isNameInUse(args[0]))
						{
							boolean isValueAndTime = false;
							boolean isStat = false;
							Player aPlayer = aTale.getPlayer(args[0]);
							aPC = aPlayer.getMyCharacter();
							aTurn = aPlayer.getMyTurn();

							try
							{
								value = Integer.parseInt(args[1]);
								time = Integer.parseInt(args[4]);
								isValueAndTime = true;
							}
							catch (Exception ec)
							{
								isValueAndTime = false;
							}

							if (isStat)
							{
								if (!aTurn.isBonusExisting(args[3]))
								{
									if (isValueAndTime)
									{
										for (int j = 0; j < STATNAMES.length; j++)
										{
											if (args[2].equals(STATNAMES[j]))
											{
												isStat = true;
											}
										}

										if (isStat)
										{
											isOk = true;
										}
										else
										{
											System.out.println("Not a THDL-Stat");
											writer.writeError("The stats for THDL are: str, vit, int, psy, dex, act");
										}
									}
									else
									{
										System.out.println("Time and/or value are/is not a number");
										writer.writeError("Time and value should both be a number");
									}
								}
								else
								{
									System.out.println("Bonus is already existend");
									writer.writeInfo("A bonus with this cause is already existing for this character");
								}
							}
						}
						else
						{
							System.out.println("Character is not in the tale");
							writer.writeInfo("This character is not in this tale");
						}
					}
					else
					{
						System.out.println(ErrorMessages.FORMATERROR);
						writer.writeInfo(
						        "You should use the layout -giveB [character] [value] [stat] [cause] [time in rounds] for this command");
					}
					// }
				}
				else
				{
					System.out.println("The tale has not started yet");
					writer.writeError("You have to start your tale first");
				}
			}
			else
			{
				System.out.println(ErrorMessages.PERMISSIONERROR);
				writer.writeError("You are not the storyteller of this tale");
			}
		}
		else
		{
			System.out.println(ErrorMessages.CHANNELERROR);
			writer.writePrivate("Wrong channel", e.getAuthor());
		}

		return isOk;
	}

	@Override
	public void action(String[] args, GuildMessageReceivedEvent e)
	{
		aPC.addBonus(value, args[2]);
		aTurn.addBonus(value, args[2], args[3], time);
		writer.writeSuccess("The character " + args[0] + " got a Bonus/Malus of " + value + " on " + args[2]
		        + " because of " + args[3] + " for " + time + " rounds");
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		// TODO Auto-generated method stub
		if (success)
		{
			System.out.println("The Command giveB was executed with success");
		}
		else
		{
			System.out.println("The Command giveB could not be executed with success");
		}

		writer = null;
		value = 0;
		time = 0;
		aPC = null;
		aTurn = null;
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
