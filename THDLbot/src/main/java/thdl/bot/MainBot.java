package thdl.bot;

import javax.security.auth.login.LoginException;
import commands.CmdCreateNewTale;
import commands.CmdDiceEight;
import commands.CmdDiceFour;
import commands.CmdDiceHundred;
import commands.CmdDiceSix;
import commands.CmdDiceTen;
import commands.CmdDiceTwelve;
import commands.CmdDiceTwenty;
import listeners.CommandListener;
import listeners.ReadyListener;
import listeners.ReconnectedListener;
import listeners.ShutdownListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import util.Secrets;

public class MainBot
{
	public static JDABuilder builder;

	public static void main(String[] args)
	{
		JDA jda = null;

		builder = new JDABuilder(AccountType.BOT);
		builder.setToken(Secrets.token);
		builder.setAutoReconnect(true);
		builder.setStatus(OnlineStatus.ONLINE);

		addListeners();
		addCommands();

		try
		{
			jda = builder.buildBlocking();
		}
		catch (LoginException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// catch (RateLimitedException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	public static void addCommands()
	{
		CommandHandler.commands.put("w4", new CmdDiceFour());
		CommandHandler.commands.put("w6", new CmdDiceSix());
		CommandHandler.commands.put("w8", new CmdDiceEight());
		CommandHandler.commands.put("w10", new CmdDiceTen());
		CommandHandler.commands.put("w12", new CmdDiceTwelve());
		CommandHandler.commands.put("w20", new CmdDiceTwenty());
		CommandHandler.commands.put("w100", new CmdDiceHundred());
		CommandHandler.commands.put("createPnP", new CmdCreateNewTale());
	}

	private static void addListeners()
	{
		builder.addEventListener(new ReadyListener());
		builder.addEventListener(new CommandListener());
		builder.addEventListener(new ShutdownListener());
		builder.addEventListener(new ReconnectedListener());
	}
}
