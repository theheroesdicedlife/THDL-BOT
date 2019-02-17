package thdl.commands.guildMessage;


import java.util.ArrayList;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.GuildController;
import thdl.discord.ThdlMember;
import thdl.factories.discord.RoleFactory;
import thdl.factories.discord.TextChannelFactory;
import thdl.factories.discord.ThdlMemberFactory;
import thdl.factories.discord.VoiceChannelFactory;
import thdl.factories.rpg.TaleFactory;
import thdl.log.LogMessageType;
import thdl.log.Logger;
import thdl.log.LoggerManager;
import thdl.util.DiscordID;
import thdl.util.DiscordWriter;


public class CmdCreateNewTale implements Command
{

	private DiscordWriter			writer			= null;
	private Logger					logWriter		= null;
	private GuildController			controller		= null;
	private ArrayList<Permission>	allowTxt		= null;
	private ArrayList<Permission>	denyTxt			= null;
	private ArrayList<Permission>	allowVc			= null;
	private ArrayList<Permission>	denyVc			= null;
	private ArrayList<Permission>	denyEveryoneTxt	= null;
	private ArrayList<Permission>	denyEveryoneVc	= null;

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		boolean isOk = false;
		String talename = "";
		writer = DiscordWriter.createWriter(writer, e);
		logWriter = LoggerManager.getLogger(ILogGuildCmd.NUM, ILogGuildCmd.NAME);

		ThdlMember member = ThdlMemberFactory.getMember(e.getAuthor());

		if (member != null)
		{
			if (member.isStoryteller())
			{
				if (args.length == 1)
				{
					talename = args[0];
					if (!TaleFactory.isNameInUse(talename))
					{
						isOk = true;
					}
					else
					{
						isOk = false;
						logWriter.addMessageToLog(this.toString(), LogMessageType.INFO, ILogGuildCmd.NAME_IN_USE,
								IGuildMsgCmd.INFO_TALE_NAME_IN_USE);
						writer.writeInfo(IGuildMsgCmd.INFO_TALE_NAME_IN_USE);
					}
				}
				else
				{
					isOk = false;
					logWriter.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogGuildCmd.WRONG_FORMAT,
							IGuildMsgCmd.INFO_FORMAT_CREATE_TALE);
					writer.writeInfo(IGuildMsgCmd.INFO_FORMAT_CREATE_TALE);
				}
			}
			else
			{
				isOk = false;
				logWriter.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogGuildCmd.UNAUTHORIZED_USE,
						IGuildMsgCmd.ERROR_UNAUTHORIZED);
				writer.writePrivate(IGuildMsgCmd.ERROR_UNAUTHORIZED, e.getAuthor());
			}
		}
		else
		{
			isOk = false;
			String error = e.getGuild().getOwner().getAsMention() + " " + IGuildMsgCmd.ERROR_NO_MEMBER;
			logWriter.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogGuildCmd.NO_MEMBER,
					IGuildMsgCmd.ERROR_NO_MEMBER);
			writer.writeError(error);
		}

		return isOk;
	}

	@Override
	public void action(String[] args, GuildMessageReceivedEvent e)
	{
		try
		{
			createNewTale(args[0], e);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
			logWriter.addMessageToLog(this.toString(), LogMessageType.EXCEPTION, ILogGuildCmd.CREATE_TALE_EXCEPTION,
					e1.getMessage());
		}
	}

	/**
	 * Creates a new Tale, first it creates a new Role with the RoleFactory, second
	 * a mainchannel with the TextChannelFactory and third a secondary Channel with
	 * the VoiceChannelFactory
	 * At last it creates the Tale with the TaleFactory, and adds the new Tale to
	 * the Collection inside of the factory
	 * 
	 * @param talename
	 *            Name of the new Tale
	 * @param e
	 *            Event, which causes the usage of the command
	 * @throws Exception
	 */
	private void createNewTale(String talename, GuildMessageReceivedEvent e) throws Exception
	{
		Guild guild = e.getGuild();
		controller = openControl(e);

		Category parent = guild.getCategoryById(DiscordID.RPGTXTCAT_ID);
		Role everyone = guild.getPublicRole();
		ThdlMember author = ThdlMemberFactory.getMember(e.getAuthor());

		initPermissions();
		String voicename = talename + "_Voice";

		Role role = RoleFactory.createRole(controller, talename);

		if (role != null)
		{
			logWriter.logState(this.toString(), ILogGuildCmd.ROLE_CREATE);

			controller.addSingleRoleToMember(e.getMember(), role).queue();

			TextChannel mainChannel = TextChannelFactory.createTextChannel(controller, talename, parent, everyone, role,
					null, allowTxt, denyEveryoneTxt, denyTxt);

			if (mainChannel != null)
			{
				logWriter.logState(this.toString(), ILogGuildCmd.CHANNEL_CREATE);

				VoiceChannel secondaryChannel = VoiceChannelFactory.createVoiceChannel(controller, voicename, parent,
						everyone, role, null, allowVc, denyEveryoneVc, denyVc);

				if (secondaryChannel != null)
				{
					logWriter.logState(this.toString(), ILogGuildCmd.CHANNEL_CREATE);

					TaleFactory.createTale(talename, author, role, mainChannel, secondaryChannel);
					writer.writeSuccess(
							IGuildMsgCmd.SUC_PNP_CREATED_FIRST + talename + IGuildMsgCmd.SUC_PNP_CREATED_AFTER);
					// pnp.safeNew();
				}
				else
				{
					logWriter.logErrorWithoutMsg(this.toString(), ILogGuildCmd.NO_CHANNEL);
				}
			}
			else
			{
				logWriter.logErrorWithoutMsg(this.toString(), ILogGuildCmd.NO_CHANNEL);
			}
		}
		else
		{
			logWriter.logErrorWithoutMsg(this.toString(), ILogGuildCmd.NO_ROLE);
		}
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		if (success)
		{
			logWriter.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_CREATE_PNP_SUCCESS);
		}
		else
		{
			logWriter.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_CREATE_PNP_FAILED);
		}

		writer = null;
		controller = null;
		allowTxt = null;
		denyTxt = null;
		allowVc = null;
		denyTxt = null;
	}

	/**
	 * Initializes the permissions for the new Voice- and TextChannel
	 */
	private void initPermissions()
	{
		allowTxt = new ArrayList<>();
		denyTxt = new ArrayList<>();
		allowVc = new ArrayList<>();
		denyVc = new ArrayList<>();
		denyEveryoneTxt = new ArrayList<>();
		denyEveryoneVc = new ArrayList<>();

		allowVc.add(Permission.VIEW_CHANNEL);
		allowVc.add(Permission.VOICE_CONNECT);
		allowVc.add(Permission.VOICE_SPEAK);

		denyVc.add(Permission.MANAGE_CHANNEL);
		denyVc.add(Permission.VOICE_MOVE_OTHERS);

		allowTxt.add(Permission.VIEW_CHANNEL);
		allowTxt.add(Permission.MESSAGE_READ);
		allowTxt.add(Permission.MESSAGE_WRITE);
		allowTxt.add(Permission.MESSAGE_ADD_REACTION);
		allowTxt.add(Permission.MESSAGE_ATTACH_FILES);
		allowTxt.add(Permission.MESSAGE_EMBED_LINKS);

		denyTxt.add(Permission.MANAGE_CHANNEL);
		denyTxt.add(Permission.MESSAGE_MANAGE);

		denyEveryoneTxt.add(Permission.VIEW_CHANNEL);
		denyEveryoneTxt.add(Permission.MESSAGE_READ);
		denyEveryoneVc.add(Permission.VIEW_CHANNEL);
		denyEveryoneVc.add(Permission.VOICE_CONNECT);
	}

	@Override
	public String help()
	{
		// TODO Auto-generated method stub
		return null;
	}

	private GuildController openControl(GuildMessageReceivedEvent e)
	{
		if (controller == null)
		{
			controller = e.getGuild().getController();
		}
		return controller;
	}

}
