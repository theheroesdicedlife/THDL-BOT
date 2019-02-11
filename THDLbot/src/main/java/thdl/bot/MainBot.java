package thdl.bot;


import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import thdl.commands.guildMessage.CmdAddPlayer;
import thdl.commands.guildMessage.CmdCreateCharacter;
import thdl.commands.guildMessage.CmdCreateNewTale;
import thdl.commands.guildMessage.CmdDiceEight;
import thdl.commands.guildMessage.CmdDiceFour;
import thdl.commands.guildMessage.CmdDiceHundred;
import thdl.commands.guildMessage.CmdDiceSix;
import thdl.commands.guildMessage.CmdDiceTen;
import thdl.commands.guildMessage.CmdDiceTwelve;
import thdl.commands.guildMessage.CmdDiceTwenty;
import thdl.commands.guildMessage.CmdStartTale;
import thdl.listeners.AddRoleToMemberListener;
import thdl.listeners.CommandListener;
import thdl.listeners.ReadyListener;
import thdl.listeners.ReconnectedListener;
import thdl.listeners.RemoveRoleFromMemberListener;
import thdl.listeners.RoleCreationListener;
import thdl.listeners.RoleDeletionListener;
import thdl.listeners.RoleUpdateNameListener;
import thdl.listeners.RoleUpdatePositionListener;
import thdl.listeners.ShutdownListener;
import thdl.util.Secrets;


public class MainBot
{

	public static JDABuilder builder;

	public static void main(String[] args)
	{
		JDA jda = null;
		builder = new JDABuilder(AccountType.BOT);
		builder.setToken(Secrets.token).setAutoReconnect(true).setStatus(OnlineStatus.ONLINE);
		addListeners();
		addCommands();
		try
		{
			jda = builder.build();
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
	}

	public static void addCommands()
	{
		CommandHandler.commands.put(IMainUtil.DICE_D4_CMD, new CmdDiceFour());
		CommandHandler.commands.put(IMainUtil.DICE_D6_CMD, new CmdDiceSix());
		CommandHandler.commands.put(IMainUtil.DICE_D8_CMD, new CmdDiceEight());
		CommandHandler.commands.put(IMainUtil.DICE_D10_CMD, new CmdDiceTen());
		CommandHandler.commands.put(IMainUtil.DICE_D12_CMD, new CmdDiceTwelve());
		CommandHandler.commands.put(IMainUtil.DICE_D20_CMD, new CmdDiceTwenty());
		CommandHandler.commands.put(IMainUtil.DICE_D100_CMD, new CmdDiceHundred());
		CommandHandler.commands.put(IMainUtil.PNP_CREATION_CMD, new CmdCreateNewTale());
		CommandHandler.commands.put(IMainUtil.ADD_PLAYER_CMD, new CmdAddPlayer());
		CommandHandler.commands.put(IMainUtil.CHARACTER_CREATION_CMD, new CmdCreateCharacter());
		CommandHandler.commands.put(IMainUtil.START_THE_PNP_CMD, new CmdStartTale());
	}

	private static void addListeners()
	{
		builder.addEventListeners(new ReadyListener(), new CommandListener(), new ShutdownListener(),
				new ReconnectedListener(), new AddRoleToMemberListener(), new RemoveRoleFromMemberListener(),
				new RoleCreationListener(), new RoleDeletionListener(), new RoleUpdateNameListener(),
				new RoleUpdatePositionListener());
	}
}
