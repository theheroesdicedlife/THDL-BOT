package thdl.lib.rpg;


import java.util.ArrayList;
import java.util.HashMap;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import thdl.lib.discord.ThdlMember;
import thdl.lib.factories.rpg.RaceFactory;


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
	private ArrayList<String>			racesInTale			= null;
	private boolean						isStarted;

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
		racesInTale = new ArrayList<String>();
		RaceFactory.addStandardToTale(racesInTale);
		this.isStarted = false;
	}

	/**
	 * Is that user the storyteller of this tale
	 * 
	 * @param id
	 *            The id die of a discord-user
	 * @return boolean
	 * 
	 */
	public boolean isStoryteller(String id)
	{
		String tellerID = storyteller.getUserID();
		return tellerID.equals(id);
	}

	/**
	 * Are any members invited?
	 * 
	 * @return boolean
	 *         true if the HashMap for invites is not empty
	 */
	public boolean hasInvites()
	{
		return !invites.isEmpty();
	}

	/**
	 * Are any players in the Tale
	 * 
	 * @return boolean
	 *         true if the HashMap for players is not empty
	 */
	public boolean hasPlayer()
	{
		return !player.isEmpty();
	}

	/**
	 * Is the race with that name in the tale?
	 * 
	 * @param name
	 *            of the a THDL-Race
	 * @return boolean
	 */
	public boolean isRaceInTale(String name)
	{
		boolean isInTale = true;

		for (String race : racesInTale)
		{
			if (!race.equals(name))
			{
				isInTale = false;
			}
			else
			{
				isInTale = true;
				break;
			}
		}

		return isInTale;
	}

	/**
	 * Adds a race to the tale
	 * 
	 * @param name
	 *            of the race to add to the tale
	 */
	public void addRace(String name)
	{
		racesInTale.add(name);
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

	public boolean isStarted()
	{
		return isStarted;
	}

	public void setStarted(boolean isStarted)
	{
		this.isStarted = isStarted;
	}

	public boolean addToInviteList(ThdlMember member)
	{
		if (invites.containsKey(member.getUserID()))
		{
			return false;
		}
		else
		{
			invites.put(member.getUserID(), member);
			return true;
		}
	}
}
