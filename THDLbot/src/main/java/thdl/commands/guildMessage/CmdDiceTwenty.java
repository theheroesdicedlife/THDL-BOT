package thdl.commands.guildMessage;


import java.util.Random;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import thdl.util.DiscordWriter;
import thdl.util.log.LogMessageType;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class CmdDiceTwenty implements Command, IDiced
{

	private int				quant	= 0;
	private Logger			log		= null;
	private DiscordWriter	writer	= null;

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		writer = DiscordWriter.createWriter(writer, e);
		log = LoggerManager.getLogger(ILogGuildCmd.NUM, ILogGuildCmd.NAME);
		Boolean isCalled = false;

		if (args.length == 1)
		{
			try
			{
				quant = Integer.parseInt(args[0]);
			}
			catch (Exception exc)
			{
				log.addMessageToLog(this.toString(), LogMessageType.EXCEPTION, ILogGuildCmd.NUMBER_TRY,
						IGuildMsgCmd.EXC_NUMBER_FORMAT);
			}

			if (quant >= 1 && quant <= 100)
			{
				isCalled = true;
			}
			else
			{
				log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogGuildCmd.QUANTITIY_ERROR,
						IGuildMsgCmd.ERROR_FALSE_QUANTITY);
				writer.writeError(IGuildMsgCmd.ERROR_FALSE_QUANTITY);
			}
		}
		else if (args.length == 0)
		{
			isCalled = true;
		}
		else
		{
			isCalled = false;
			log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogGuildCmd.WRONG_FORMAT,
					IGuildMsgCmd.INFO_FORMAT_DICE_TWENTY);
			writer.writeError(IGuildMsgCmd.INFO_FORMAT_DICE_TWENTY);
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
			writer.writeSuccess(e.getMember().getNickname() + IGuildMsgCmd.SUC_DICE_TWENTY_THROW + quant
					+ IGuildMsgCmd.SUC_DICE_TIMES + IGuildMsgCmd.SUC_DICE_GETS_A + res);
		}
		else
		{
			res = diceOnce(rn);
			writer.writeSuccess(e.getMember().getNickname() + IGuildMsgCmd.SUC_DICE_TWENTY_THROW
					+ IGuildMsgCmd.SUC_DICE_GETS_A + res);
		}

		secureDiceResult(res, e);
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		if (success)
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_DICE_TWENTY_SUCCESS);
		}
		else
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_DICE_TWENTY_FAILED);
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
		String logmsg = "";

		logmsg = ILogGuildCmd.DICED + result;

		log.logState(this.toString(), logmsg);
		return result;
	}

	@Override
	public int diceMultiple(Random rand, int quantity)
	{
		int result = 0;
		String logmsg = "";
		for (int i = 0; i < quantity; i++)
		{
			result = result + rand.nextInt(20) + 1;
		}
		logmsg = ILogGuildCmd.DICED + result;

		log.logState(this.toString(), logmsg);
		return result;
	}

	@Override
	public void secureDiceResult(int result, GuildMessageReceivedEvent e)
	{
		/**
		 * TODO: Implementation missing
		 */
	}

}
