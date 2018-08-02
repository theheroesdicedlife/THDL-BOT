package commands;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import rpglib.Bonus;
import rpglib.Player;
import rpglib.PlayerCharacter;
import rpglib.Stats;
import rpglib.Tale;
import rpglib.Tales;
import rpglib.Turn;
import thdl.bot.DiscordWriter;
import util.ErrorMessages;

public class CmdRemoveABonus implements Command, Stats
{
	Player			aPlayer	= null;
	Turn			aTurn	= null;
	DiscordWriter	writer	= null;

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		Tale aTale = Tales.getTale(e.getChannel());
		boolean isOk = false;
		writer = openWriter(e);

		if (aTale != null)
		{
			String storytellerId = aTale.getStoryteller().getUser().getId();
			String authorId = e.getAuthor().getId();
			if (storytellerId.equals(authorId))
			{
				if (aTale.getIsStarted())
				{
					// if(aTale.isInBattle())
					// {
					if (args.length == 2)
					{
						if (aTale.isNameInUse(args[0]))
						{
							aPlayer = aTale.getPlayer(args[0]);
							aTurn = aPlayer.getMyTurn();
							if (aTurn.isBonusExisting(args[1]))
							{
								isOk = true;
							}
							else
							{
								System.out.println("Bonus is not existend");
								writer.writeInfo("A bonus with this cause is not existing for this character");
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
						writer.writeInfo("You should use the layout -rmB [charactername] [cause] for this command");
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
		PlayerCharacter aPC = aPlayer.getMyCharacter();
		Bonus aBonus = aTurn.removeBonus(args[1]);

		aPC.removeBonus(aBonus.getStat(), aBonus.getValue());
		writer.writeSuccess("The Bonus/Malus of " + aBonus.getCause() + " on the Stat " + STATNAMES[aBonus.getStat()]
		        + " was removed");
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		if (success)
		{
			System.out.println("The command rmB was executed with success");
		}
		else
		{
			System.out.println("The command rmB could not be executed with success");
		}
		aTurn = null;
		writer = null;
		aPlayer = null;
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
