package thdl.lib.rpg;


import java.util.HashMap;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import thdl.lib.discord.ThdlMember;


public class Tale
{

	/**
	 * The mainclass of each pnp
	 */
	private String						taleName			= "";
	private ThdlMember					storyteller			= null;
	private Role						taleRole			= null;
	private TextChannel					mainChannel			= null;
	private VoiceChannel				secondaryChannel	= null;
	private HashMap<String, ThdlMember>	invites				= null;
	private HashMap<String, ThdlMember>	player				= null;

	public Tale(String taleName, ThdlMember storyteller, Role taleRole, TextChannel mainChannel,
			VoiceChannel secondaryChannel)
	{
		super();
		this.taleName = taleName;
		this.storyteller = storyteller;
		this.taleRole = taleRole;
		this.mainChannel = mainChannel;
		this.secondaryChannel = secondaryChannel;

		init();
	}

	private void init()
	{
		invites = new HashMap<String, ThdlMember>();
		player = new HashMap<String, ThdlMember>();
	}

	public boolean isStoryteller(String id)
	{
		String tellerID = storyteller.getUserID();
		return tellerID.equals(id);
	}

	public boolean hasInvites()
	{
		return !invites.isEmpty();
	}

	public boolean hasPlayer()
	{
		return !invites.isEmpty();
	}

	public String getTaleName()
	{
		return taleName;
	}

	public ThdlMember getStoryteller()
	{
		return storyteller;
	}

	public Tale setStoryteller(ThdlMember storyteller)
	{
		this.storyteller = storyteller;
		return this;
	}

	public Role getTaleRole()
	{
		return taleRole;
	}

	public TextChannel getMainChannel()
	{
		return mainChannel;
	}

	public VoiceChannel getSecondaryChannel()
	{
		return secondaryChannel;
	}
}
