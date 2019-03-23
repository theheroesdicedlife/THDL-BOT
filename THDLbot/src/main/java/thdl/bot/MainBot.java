package thdl.bot;


import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import thdl.commands.directMessage.DmPrintLogs;
import thdl.commands.guildMessage.dice.CmdDiceEight;
import thdl.commands.guildMessage.dice.CmdDiceFour;
import thdl.commands.guildMessage.dice.CmdDiceHundred;
import thdl.commands.guildMessage.dice.CmdDiceSix;
import thdl.commands.guildMessage.dice.CmdDiceTen;
import thdl.commands.guildMessage.dice.CmdDiceTwelve;
import thdl.commands.guildMessage.dice.CmdDiceTwenty;
import thdl.commands.guildMessage.tale.CmdAddMoreRaces;
import thdl.commands.guildMessage.tale.CmdCreateNewTale;
import thdl.commands.guildMessage.tale.CmdInvitePlayerToTale;
import thdl.listeners.CommandListener;
import thdl.listeners.DirectCommandListener;
import thdl.listeners.ReadyListener;
import thdl.listeners.member.AddRoleToMemberListener;
import thdl.listeners.member.RemoveRoleFromMemberListener;
import thdl.listeners.role.RoleCreationListener;
import thdl.listeners.role.RoleDeletionListener;
import thdl.listeners.role.RoleUpdateNameListener;
import thdl.listeners.role.RoleUpdatePositionListener;
import thdl.util.ISecrets;
import thdl.util.directMessage.DirectMessageHandler;
import thdl.util.guildMessage.CommandHandler;


public class MainBot
{

	public static JDABuilder builder;

	public static void main(String[] args)
	{
		JDA jda = null;
		builder = new JDABuilder(AccountType.BOT);
		builder.setToken(ISecrets.token).setAutoReconnect(true).setStatus(OnlineStatus.ONLINE);
		addListeners();
		addCommands();
		addDirectCommands();

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
		CommandHandler.commands.put(IMainUtil.ADD_RACES_TO_PNP, new CmdAddMoreRaces());
		CommandHandler.commands.put(IMainUtil.INVITE_PLAYERS, new CmdInvitePlayerToTale());
		// CommandHandler.commands.put(IMainUtil.CHARACTER_CREATION_CMD, new
		// CmdCreateCharacter());
		// CommandHandler.commands.put(IMainUtil.START_THE_PNP_CMD, new CmdStartTale());
	}

	public static void addDirectCommands()
	{
		DirectMessageHandler.commands.put("showLog", new DmPrintLogs());
	}

	private static void addListeners()
	{
		// builder.addEventListeners(new ReadyListener(), new CommandListener(), new
		// ShutdownListener(),
		// new ReconnectedListener(), new AddRoleToMemberListener(), new
		// RemoveRoleFromMemberListener(),
		// new RoleCreationListener(), new RoleDeletionListener(), new
		// RoleUpdateNameListener(),
		// new RoleUpdatePositionListener());
		builder.addEventListeners(new CommandListener(), new ReadyListener(), new AddRoleToMemberListener(),
				new RemoveRoleFromMemberListener(), new RoleCreationListener(), new RoleDeletionListener(),
				new RoleUpdateNameListener(), new RoleUpdatePositionListener(), new DirectCommandListener());
	}
}
