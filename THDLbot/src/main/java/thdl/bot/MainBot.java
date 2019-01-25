package thdl.bot;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import thdl.commands.CmdAddPlayer;
import thdl.commands.CmdCreateCharacter;
import thdl.commands.CmdCreateNewTale;
import thdl.commands.CmdDiceEight;
import thdl.commands.CmdDiceFour;
import thdl.commands.CmdDiceHundred;
import thdl.commands.CmdDiceSix;
import thdl.commands.CmdDiceTen;
import thdl.commands.CmdDiceTwelve;
import thdl.commands.CmdDiceTwenty;
import thdl.commands.CmdStartTale;
import thdl.listeners.CommandListener;
import thdl.listeners.ReadyListener;
import thdl.listeners.ReconnectedListener;
import thdl.listeners.ShutdownListener;
import thdl.util.Secrets;

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
		CommandHandler.commands.put("addPlayer", new CmdAddPlayer());
		CommandHandler.commands.put("createCharacter", new CmdCreateCharacter());
		CommandHandler.commands.put("start", new CmdStartTale());

	}

	private static void addListeners()
	{
		builder.addEventListener(new ReadyListener());
		builder.addEventListener(new CommandListener());
		builder.addEventListener(new ShutdownListener());
		builder.addEventListener(new ReconnectedListener());
	}
}
