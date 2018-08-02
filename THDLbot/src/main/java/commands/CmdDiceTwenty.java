package commands;

import java.util.Random;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import rpglib.Tale;
import rpglib.Tales;
import rpglib.Turn;
import thdl.bot.DiscordWriter;
import util.DiscordID;
import util.ErrorMessages;

public class CmdDiceTwenty implements Command, IDiced
{

	private int				quant	= 0;
	private DiscordWriter	writer	= null;

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		writer = openWriter(e);
		Boolean isCalled = false;
		if (args.length == 1)
		{
			try
			{
				quant = Integer.parseInt(args[0]);
			}
			catch (Exception exc)
			{
				System.out.println("Argument is not an Integer");
			}

			if (quant >= 1 && quant <= 100)
			{
				isCalled = true;
			}
			else
			{
				System.out.println(ErrorMessages.QUANTITIYERROR);
				writer.writeError("Quantity is not working :frowning:");
			}
		}
		else
			if (args.length == 0)
			{
				isCalled = true;
			}
			else
			{
				isCalled = false;
				System.out.println(ErrorMessages.FORMATERROR);
				writer.writeError("Please use the format -w20 [quantity] :weary:");
			}
		return isCalled;
	}

	@Override
	public void action(String[] args, GuildMessageReceivedEvent e)
	{
		Random rn = new Random();
		int res = 0;

		if (quant > 0)
		{
			res = diceMultiple(rn, quant);
			writer.writeSuccess(
			        e.getMember().getNickname() + " throws a 20 sided dice " + quant + " times and gets a " + res);
		}
		else
		{
			res = diceOnce(rn);
			writer.writeSuccess(e.getMember().getNickname() + " throws a 20 sided dice and gets a " + res);
		}

		secureDiceResult(res, e);
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		if (success)
		{
			System.out.println("Command -w20 was executed with success");
		}
		else
		{
			System.out.println("Command -w20 could not be executed successfully");
		}
		quant = 0;
		writer = null;
	}

	@Override
	public String help()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int diceOnce(Random rand)
	{
		int result = rand.nextInt(20) + 1;
		System.out.println("Diced: " + result);
		return result;
	}

	@Override
	public int diceMultiple(Random rand, int quantity)
	{
		int result = 0;
		for (int i = 0; i < quantity; i++)
		{
			result = result + rand.nextInt(20) + 1;
		}
		System.out.println("Diced: " + result);
		return result;
	}

	@Override
	public void secureDiceResult(int result, GuildMessageReceivedEvent e)
	{
		TextChannel txt = e.getChannel();
		if (txt.getParent().getId().equals(DiscordID.RPGTXTCAT_ID))
		{
			Tale s = Tales.getTale(e.getChannel());
			if (s.getIsStarted())
			{
				Turn t = s.getCurrentTurn();
				// TODO: Save result in Story, till next dice is thrown (later maybe two)
				String currentPlayerId = t.getMyPlayer().getMySelf().getUser().getId();
				String authorId = e.getAuthor().getId();
				if (currentPlayerId.equals(authorId))
				{
					if (t.secureResultOfDice(result))
					{
						System.out.println("Result is saved in turn");
					}
					else
					{
						System.out.println("Dice-limit reached");
						writer.writeError("You can only throw two dices per turn");
					}

				}
				else
				{
					System.out.println("Not the turn of " + authorId + " - " + e.getAuthor().getName());
				}
			}
			else
			{
				System.out.println("No need to safe result");
			}
		}
		else
		{
			System.out.println("No need to safe result");
		}
	}

	public DiscordWriter openWriter(GuildMessageReceivedEvent e)
	{
		if (writer == null)
		{
			writer = new DiscordWriter(e);
		}
		return writer;
	}

}
