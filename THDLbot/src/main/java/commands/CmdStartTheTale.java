package commands;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import rpglib.Tale;
import rpglib.Tales;
import rpglib.Turn;
import thdl.bot.DiscordWriter;
import util.ErrorMessages;

public class CmdStartTheTale implements Command
{
	private DiscordWriter	writer	= null;
	private Tale			aTale	= null;

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		writer = openWriter(e);
		boolean isOk = false;
		aTale = Tales.getTale(e.getChannel());

		if (aTale != null)
		{
			String authorId = e.getAuthor().getId();
			String storytellerId = aTale.getStoryteller().getUser().getId();

			if (authorId.equals(storytellerId))
			{
				if (args.length == 0)
				{
					if (!aTale.getIsStarted())
					{
						if (aTale.getNumberOfPlayers() <= 2)
						{
							isOk = true;
						}
						else
						{
							System.out.println("Playercount to low");
							writer.writeError("You need more players to start a tale");
						}
					}
					else
					{
						System.out.println(ErrorMessages.STARTINGERROR);
						writer.writeInfo("Tale was started before");
					}
				}
				else
				{
					System.out.println(ErrorMessages.FORMATERROR);
					writer.writeInfo("You should use the layout -startTale for this command");
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
		aTale.getOrder();
		aTale.setIsStarted(true);
		Turn aTurn = aTale.getCurrentTurn();
		Member memberOfTurn = aTurn.getMyPlayer().getMySelf();
		writer.writeSuccess("The tale " + aTale.getName() + " was started");
		writer.writeInfo(memberOfTurn.getAsMention() + " make yourself ready the first turn is yours");
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		if (success)
		{
			System.out.println("The command startTale was executed with success");
		}
		else
		{
			System.out.println("The command startTale could not be executed with success");
		}

		writer = null;
		aTale = null;
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
