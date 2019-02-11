package thdl.rpg.lib;


import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import thdl.discord.ThdlMember;


public class Tale
{

	/**
	 * The mainclass of each pnp
	 */
	private String			taleName			= "";
	private ThdlMember		storyteller			= null;
	private Role			taleRole			= null;
	private TextChannel		mainChannel			= null;
	private VoiceChannel	secondaryChannel	= null;

	public Tale(String taleName, ThdlMember storyteller, Role taleRole, TextChannel mainChannel,
			VoiceChannel secondaryChannel)
	{
		super();
		this.taleName = taleName;
		this.storyteller = storyteller;
		this.taleRole = taleRole;
		this.mainChannel = mainChannel;
		this.secondaryChannel = secondaryChannel;
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
