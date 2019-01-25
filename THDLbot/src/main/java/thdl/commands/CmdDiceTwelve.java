package thdl.commands;

import java.util.Random;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import thdl.bot.DiscordWriter;
import thdl.util.ErrorMessages;

public class CmdDiceTwelve implements Command, IDiced
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
				System.out.println(ErrorMessages.QUANTITIY_ERROR);
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
				System.out.println(ErrorMessages.FORMAT_ERROR);
				writer.writeError("Please use the format -w12 [quantity] :weary:");
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
			        e.getMember().getNickname() + " throws a 12 sided dice " + quant + " times and gets a " + res);
		}
		else
		{
			res = diceOnce(rn);
			writer.writeSuccess(e.getMember().getNickname() + " throws a 12 sided dice and gets a " + res);
		}

		secureDiceResult(res, e);
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		if (success)
		{
			System.out.println("Command -w12 was executed with success");
		}
		else
		{
			System.out.println("Command -w12 could not be executed successfully");
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
		int result = rand.nextInt(12) + 1;
		System.out.println("Diced: " + result);
		return result;
	}

	@Override
	public int diceMultiple(Random rand, int quantity)
	{
		int result = 0;
		for (int i = 0; i < quantity; i++)
		{
			result = result + rand.nextInt(12) + 1;
		}
		System.out.println("Diced: " + result);
		return result;
	}

	@Override
	public void secureDiceResult(int result, GuildMessageReceivedEvent e)
	{

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
