package commands;

import java.awt.Color;
import java.util.ArrayList;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.requests.restaction.ChannelAction;
import net.dv8tion.jda.core.requests.restaction.RoleAction;
import rpglib.Tale;
import rpglib.Tales;
import thdl.bot.DiscordMapper;
import thdl.bot.DiscordWriter;
import util.DiscordID;
import util.ErrorMessages;

public class CmdCreateNewTale implements Command
{
	private DiscordWriter			writer		= null;
	private GuildController			controller	= null;
	private ArrayList<Permission>	allowTxt	= null;
	private ArrayList<Permission>	denyTxt		= null;
	private ArrayList<Permission>	allowVc		= null;
	private ArrayList<Permission>	denyVc		= null;

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		boolean isOk = false;
		String talename = "";
		writer = openWriter(e);
		controller = openControl(e);

		if (DiscordMapper.isThisA(e.getMember(), "~Storyteller"))
		{
			if (args.length == 1)
			{
				talename = args[0];
				if (!Tales.isNameInUse(talename))
				{
					isOk = true;
				}
				else
				{
					System.out.println("Name is in use");
					writer.writeError("I'm sorry. The name for your tale is already in use");
				}
			}
			else
			{
				System.out.println(ErrorMessages.FORMATERROR);
				writer.writeError("You should use the format -createTale [talename]");
			}
		}
		else
		{
			System.out.println(ErrorMessages.PERMISSIONERROR);
			writer.writeError("You are not a Storyteller");
		}

		return isOk;
	}

	@Override
	public void action(String[] args, GuildMessageReceivedEvent e)
	{
		createNewTale(args[0], e);
	}

	private void createNewTale(String talename, GuildMessageReceivedEvent e)
	{
		Tale pnp = new Tale(talename, e.getMember());
		Guild guild = e.getGuild();

		Role role = createRole(talename);

		initPermissions();

		if (role != null)
		{
			TextChannel txtchan = createTextChannel(talename, guild, role);
			if (txtchan != null)
			{
				VoiceChannel vcchan = createVoiceChannel(talename, guild, role);

				if (vcchan != null)
				{
					pnp.setBasics(role, txtchan, vcchan);
					Tales.addTale(pnp);
					writer.writeSuccess("The THDL-PnP " + talename + " was created");
				}
			}
		}
	}

	private Role createRole(String talename)
	{
		String rolename = talename + "_Role";
		Role role = null;

		try
		{
			RoleAction rac = controller.createRole();
			rac.setName(rolename);
			rac.setColor(Color.GRAY);

			role = rac.complete();
		}
		catch (Exception e)
		{
			System.out.println("Error while creating Role");
			e.printStackTrace();
		}

		return role;
	}

	private TextChannel createTextChannel(String talename, Guild g, Role role)
	{
		TextChannel txtchan = null;
		Category parent = g.getCategoryById(DiscordID.RPGTXTCAT_ID);

		try
		{
			ChannelAction cac = controller.createTextChannel(talename);
			cac.setParent(parent);
			cac.addPermissionOverride(role, allowTxt, denyTxt);

			txtchan = (TextChannel) cac.complete();
		}
		catch (Exception e)
		{
			System.out.println("Error while creating TextChannel");
			e.printStackTrace();
		}

		return txtchan;
	}

	private VoiceChannel createVoiceChannel(String talename, Guild g, Role role)
	{
		VoiceChannel vcchan = null;

		String voicename = talename + "_Voice";
		Category parent = g.getCategoryById(DiscordID.RPGVCCAT_ID);

		try
		{
			ChannelAction cac = controller.createVoiceChannel(voicename);
			cac.setParent(parent);
			cac.addPermissionOverride(role, allowTxt, denyTxt);

			vcchan = (VoiceChannel) cac.complete();
		}
		catch (Exception e)
		{
			System.out.println("Error while creating VoiceChannel");
			e.printStackTrace();
		}

		return vcchan;
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		if (success)
		{
			System.out.println("Command createPnP was executed with success");
		}
		else
		{
			System.out.println("Command createPnP could not be executed with success");
		}
		writer = null;
		controller = null;
		allowTxt = null;
		denyTxt = null;
		allowVc = null;
		denyTxt = null;
	}

	private void initPermissions()
	{
		allowTxt = new ArrayList<>();
		denyTxt = new ArrayList<>();
		allowVc = new ArrayList<>();
		denyVc = new ArrayList<>();

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
	}

	@Override
	public String help()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiscordWriter openWriter(GuildMessageReceivedEvent e)
	{
		if (writer == null)
		{
			writer = new DiscordWriter(e);
		}
		return writer;
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
