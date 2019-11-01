package thdl.commands.guildMessage.tale;


import java.sql.SQLException;
import java.util.ArrayList;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.GuildController;
import thdl.commands.guildMessage.ILogGuildCmd;
import thdl.lib.discord.ThdlMember;
import thdl.lib.factories.discord.RoleFactory;
import thdl.lib.factories.discord.TextChannelFactory;
import thdl.lib.factories.discord.ThdlMemberFactory;
import thdl.lib.factories.discord.VoiceChannelFactory;
import thdl.lib.factories.rpg.TaleFactory;
import thdl.util.IDiscordID;
import thdl.util.log.Logger;


public class TaleCreationHandler
{

	private ArrayList<Permission>	allowTxt		= null;
	private ArrayList<Permission>	denyTxt			= null;
	private ArrayList<Permission>	allowVc			= null;
	private ArrayList<Permission>	denyVc			= null;
	private ArrayList<Permission>	denyEveryoneTxt	= null;
	private ArrayList<Permission>	denyEveryoneVc	= null;

	/**
	 * Constructs a new tale, while building some additional discord-objects for it.
	 * First a Role is created. Second a TextChannel and a Voice Channel.
	 * 
	 * @param talename
	 *            name of the tale
	 * @param event
	 *            the event from the create tale command
	 * @param log
	 *            the logger in the command
	 * @return true if the tale was created
	 * @throws Exception
	 */
	public boolean createNewTale(String talename, GuildMessageReceivedEvent event, Logger log) throws Exception
	{
		Guild guild = event.getGuild();
		GuildController controller = event.getGuild().getController();
		Role role = null;
		TextChannel textChannel = null;
		VoiceChannel voiceChannel = null;
		boolean created = false;

		this.initPermissions();

		role = this.createRole(controller, talename);

		if (role == null)
		{
			log.logState(this.toString(), ILogGuildCmd.ROLE_CREATE);
			controller.addSingleRoleToMember(event.getMember(), role);

			textChannel = this.createTextChannel(controller, guild, talename, role);

			if (textChannel == null)
			{
				log.logState(this.toString(), ILogGuildCmd.CHANNEL_CREATE);
				voiceChannel = this.createVoiceChannel(controller, guild, talename, role);

				if (voiceChannel == null)
				{
					log.logState(this.toString(), ILogGuildCmd.CHANNEL_CREATE);

					created = this.createTale(talename, role, textChannel, voiceChannel, event);
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

		return created;
	}

	/**
	 * Creates a Role role for the new Tale
	 * 
	 * @param controller
	 *            controller of the guild
	 * @param talename
	 *            name of the new Tale
	 * @return the newly created role
	 * @throws Exception
	 */
	private Role createRole(GuildController controller, String talename) throws Exception
	{
		return RoleFactory.getInstance().createRole(controller, talename);
	}

	/**
	 * Creates a TextChannel for the new Tale (the channel that can be used to use
	 * commands for the tale)
	 * 
	 * @param controller
	 *            the controller of the guild
	 * @param guild
	 *            the guild in which the channel is created
	 * @param talename
	 *            the name of the tale
	 * @param role
	 *            the role of the tale
	 * @return the newly created textchannel
	 * @throws Exception
	 */
	private TextChannel createTextChannel(GuildController controller, Guild guild, String talename, Role role)
			throws Exception
	{
		TextChannel textChannel = null;
		Category category = null;
		Role everyone = null;

		category = guild.getCategoryById(IDiscordID.RPGTXTCAT_ID);
		everyone = guild.getPublicRole();

		textChannel = TextChannelFactory.getInstance().createTextChannel(controller, talename, category, everyone, role,
				null, allowTxt, denyEveryoneTxt, denyTxt);

		return textChannel;
	}

	/**
	 * Creates a VoiceChannel for the new Tale
	 * 
	 * @param controller
	 *            controller of the guild
	 * @param guild
	 *            the guild in which the channel is created
	 * @param talename
	 *            the name of the tale
	 * @param role
	 *            the role of the tale
	 * @return the newly created voicechannel
	 * @throws Exception
	 */
	private VoiceChannel createVoiceChannel(GuildController controller, Guild guild, String talename, Role role)
			throws Exception
	{
		VoiceChannel voiceChannel = null;
		Category category = null;
		Role everyone = null;
		String voicename = talename + "_Voice";

		category = guild.getCategoryById(IDiscordID.RPGVCCAT_ID);
		everyone = guild.getPublicRole();

		voiceChannel = VoiceChannelFactory.getInstance().createVoiceChannel(controller, voicename, category, everyone,
				role, null, allowVc, denyEveryoneVc, denyVc);

		return voiceChannel;
	}

	/**
	 * Finally creates the tale itsself
	 * 
	 * @param talename
	 *            Name of the tale
	 * @param role
	 *            Role of the Tale
	 * @param textChannel
	 *            TextChannel of the Tale
	 * @param voiceChannel
	 *            VoiceChannel of the Tale
	 * @param event
	 *            The GuildMessageEvent for the create-command
	 * @return
	 * 		was the tale created or was there an error
	 * @throws SQLException
	 */
	private boolean createTale(String talename, Role role, TextChannel textChannel, VoiceChannel voiceChannel,
			GuildMessageReceivedEvent event) throws SQLException
	{
		ThdlMember dungeonmaster = ThdlMemberFactory.getInstance().getMember(event.getAuthor());

		return TaleFactory.getInstance().createTale(talename, dungeonmaster, role, textChannel, voiceChannel);
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

}
