package thdl.commands.guildMessage.tale;


import java.util.ArrayList;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.GuildController;
import thdl.commands.guildMessage.Command;
import thdl.commands.guildMessage.IGuildMsgCmd;
import thdl.commands.guildMessage.ILogGuildCmd;
import thdl.lib.discord.ThdlMember;
import thdl.lib.factories.discord.RoleFactory;
import thdl.lib.factories.discord.TextChannelFactory;
import thdl.lib.factories.discord.ThdlMemberFactory;
import thdl.lib.factories.discord.VoiceChannelFactory;
import thdl.lib.factories.rpg.TaleFactory;
import thdl.util.DirectWriter;
import thdl.util.DiscordWriter;
import thdl.util.IDiscordID;
import thdl.util.log.LogMessageType;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class CmdCreateNewTale implements Command
{

	private DiscordWriter			writer			= null;
	private DirectWriter			dmWriter		= null;
	private Logger					log				= null;
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
		log = LoggerManager.getLogger(ILogGuildCmd.NUM, ILogGuildCmd.NAME);
		writer = new DiscordWriter(e);
		try
		{
			dmWriter = new DirectWriter(e.getAuthor());
		}
		catch (Exception e1)
		{
			log.logException(this.toString(), ILogGuildCmd.OPEN_DM_CHANNEL, e1.getMessage());

		}

		ThdlMember member = ThdlMemberFactory.getInstance().getMember(e.getAuthor());

		if (member != null)
		{
			if (member.isStoryteller())
			{
				if (args.length == 1)
				{
					talename = args[0];
					if (!TaleFactory.getInstance().isNameInUse(talename))
					{
						isOk = true;
					}
					else
					{
						isOk = false;
						log.logInfo(this.toString(), ILogGuildCmd.NAME_IN_USE, IGuildMsgCmd.INFO_TALE_NAME_IN_USE);
						writer.writeInfo(IGuildMsgCmd.INFO_TALE_NAME_IN_USE);
					}
				}
				else
				{
					isOk = false;
					log.logInfo(this.toString(), ILogGuildCmd.WRONG_FORMAT, IGuildMsgCmd.INFO_FORMAT_CREATE_TALE);
					writer.writeInfo(IGuildMsgCmd.INFO_FORMAT_CREATE_TALE);
				}
			}
			else
			{
				isOk = false;

				log.logErrorWithoutMsg(this.toString(), ILogGuildCmd.UNAUTHORIZED_USE);
				dmWriter.writeMsg(IGuildMsgCmd.ERROR_UNAUTHORIZED);
			}
		}
		else
		{
			isOk = false;
			String error = e.getGuild().getOwner().getAsMention() + " " + IGuildMsgCmd.ERROR_NO_MEMBER;
			log.addMessageToLog(this.toString(), LogMessageType.ERROR, ILogGuildCmd.NO_MEMBER,
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
			log.logException(this.toString(), ILogGuildCmd.CREATE_TALE_EXCEPTION, e1.getMessage());
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

		Category parentTxt = guild.getCategoryById(IDiscordID.RPGTXTCAT_ID);
		Category parentVc = guild.getCategoryById(IDiscordID.RPGVCCAT_ID);
		Role everyone = guild.getPublicRole();
		ThdlMember author = ThdlMemberFactory.getInstance().getMember(e.getAuthor());

		initPermissions();
		String voicename = talename + "_Voice";

		Role role = RoleFactory.getInstance().createRole(controller, talename);

		if (role != null)
		{
			log.logState(this.toString(), ILogGuildCmd.ROLE_CREATE);

			controller.addSingleRoleToMember(e.getMember(), role).queue();

			TextChannel mainChannel = TextChannelFactory.getInstance().createTextChannel(controller, talename,
					parentTxt, everyone, role, null, allowTxt, denyEveryoneTxt, denyTxt);

			if (mainChannel != null)
			{
				log.logState(this.toString(), ILogGuildCmd.CHANNEL_CREATE);

				VoiceChannel secondaryChannel = VoiceChannelFactory.getInstance().createVoiceChannel(controller,
						voicename, parentVc, everyone, role, null, allowVc, denyEveryoneVc, denyVc);

				if (secondaryChannel != null)
				{
					log.logState(this.toString(), ILogGuildCmd.CHANNEL_CREATE);

					if (TaleFactory.getInstance().createTale(talename, author, role, mainChannel, secondaryChannel))
					{
						writer.writeSuccess(
								IGuildMsgCmd.SUC_PNP_CREATED_FIRST + talename + IGuildMsgCmd.SUC_PNP_CREATED_AFTER);
					}
				}
				else
				{
					log.logErrorWithoutMsg(this.toString(), ILogGuildCmd.NO_CHANNEL);
				}
			}
			else
			{
				log.logErrorWithoutMsg(this.toString(), ILogGuildCmd.NO_CHANNEL);
			}
		}
		else
		{
			log.logErrorWithoutMsg(this.toString(), ILogGuildCmd.NO_ROLE);
		}
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		if (success)
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_CREATE_PNP_SUCCESS);
		}
		else
		{
			log.addMessageToLog(this.toString(), LogMessageType.STATE, ILogGuildCmd.CMD_EXE,
					ILogGuildCmd.CMD_CREATE_PNP_FAILED);
		}

		writer = null;
		dmWriter = null;
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
