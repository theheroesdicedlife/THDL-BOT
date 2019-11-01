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

	// Basic Attributes

	private String			taleName					= "";
	private ThdlMember		dungeonmaster				= null;
	private Role			taleRole					= null;
	private TextChannel		mainChannel					= null;
	private VoiceChannel	secondaryChannel			= null;
	private MasterTurn		masterTurn					= null;
	private boolean			isStarted					= false;
	private boolean			isCharacterCreationEnabled	= false;
	private boolean			startJobsEnabled			= true;

	// lists

	private HashMap<String, ThdlMember>	player		= null;
	private ArrayList<String>			racesInTale	= null;
	private ArrayList<Turn>				playerTurns	= null;

	// CONSTRUCTOR

	public Tale(String taleName, ThdlMember dungeonmaster, Role taleRole, TextChannel mainChannel,
			VoiceChannel secondaryChannel)
	{
		super();
		this.taleName = taleName;
		this.dungeonmaster = dungeonmaster;
		this.taleRole = taleRole;
		this.mainChannel = mainChannel;
		this.secondaryChannel = secondaryChannel;
		this.masterTurn = new MasterTurn(dungeonmaster, this);

		init();
	}

	// INITIALIZATION

	private void init()
	{
		player = new HashMap<String, ThdlMember>();
		racesInTale = new ArrayList<String>();
		playerTurns = new ArrayList<Turn>();
		RaceFactory.getInstance().addStandardToTale(racesInTale);
		this.isStarted = false;
		this.isCharacterCreationEnabled = false;
	}

	// FLAGS

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
		String tellerID = dungeonmaster.getUserID();
		return tellerID.equals(id);
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
	 * Checks if a member is in the player list of the tale
	 * 
	 * @param member
	 *            the member to check
	 * @return
	 * 		member is in tale or not
	 */
	public boolean isMemberAlreadyInTale(ThdlMember member)
	{
		if (player.containsKey(member.getUserID()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	// ADDER

	/**
	 * Adds a race to the tale
	 * 
	 * @param name
	 *            of the race to add to the tale
	 */
	public void addRace(String name)
	{
		if (!isRaceInTale(name))
		{
			racesInTale.add(name);
		}
	}

	/**
	 * 
	 * @param turn
	 *            turn-Object of a player in this tale
	 */
	public void addTurn(Turn turn)
	{
		playerTurns.add(turn);
		addPlayer(turn.getMember());
	}

	/**
	 * 
	 * @param member
	 *            a new player in this tale
	 */
	public void addPlayer(ThdlMember member)
	{
		player.put(member.getUserID(), member);
	}

	// REMOVER

	/**
	 * 
	 * @param name
	 *            of race to be removed
	 */
	public void removeRace(String name)
	{
		racesInTale.remove(name);
	}

	// GETTER AND SETTER

	/**
	 * 
	 * @return
	 * 		the name of this tale
	 */
	public String getTaleName()
	{
		return taleName;
	}

	/**
	 * 
	 * @return
	 * 		the dungeonmaster for this tale
	 */
	public ThdlMember getDungeonmaster()
	{
		return dungeonmaster;
	}

	/**
	 * 
	 * @param dungeonmaster
	 *            sets the dungeonmaster, do not use unless in creation of tale or
	 *            change of teller
	 * @return
	 * 		this tale
	 */
	public Tale setDungeonmaster(ThdlMember dungeonmaster)
	{
		this.dungeonmaster = dungeonmaster;
		return this;
	}

	/**
	 * 
	 * @return
	 * 		the role created exclusively for this tale
	 */
	public Role getTaleRole()
	{
		return taleRole;
	}

	/**
	 * 
	 * @return
	 * 		the textchannel created exclusively for this tale
	 */
	public TextChannel getMainChannel()
	{
		return mainChannel;
	}

	/**
	 * 
	 * @return
	 * 		the voicechannel created exclusively for this tale
	 */
	public VoiceChannel getSecondaryChannel()
	{
		return secondaryChannel;
	}

	/**
	 * @return the masterTurn
	 */
	public MasterTurn getMasterTurn()
	{
		return masterTurn;
	}

	/**
	 * 
	 * @return
	 * 		is the tale started or is it paused
	 */
	public boolean isStarted()
	{
		return isStarted;
	}

	/**
	 * toggles the start-flag
	 */
	public void toggleStarted()
	{
		if (isStarted)
		{
			isStarted = false;
		}
		else
		{
			isStarted = true;
		}
	}

	/**
	 * 
	 * @return
	 * 		all Turns for players
	 */
	public ArrayList<Turn> getPlayerTurns()
	{
		return playerTurns;
	}

	/**
	 * 
	 * @return
	 * 		the flag for enabled disabled start-jobs.
	 */
	public boolean isStartJobsEnabled()
	{
		return startJobsEnabled;
	}

	/**
	 * toggles the start-job flag
	 */
	public void toggleStartJobs()
	{
		if (startJobsEnabled)
		{
			startJobsEnabled = false;
		}
		else
		{
			startJobsEnabled = true;
		}
	}

	/**
	 * @return the isCharacterCreationEnabled
	 */
	public boolean isCharacterCreationEnabled()
	{
		return isCharacterCreationEnabled;
	}

	/**
	 * toggles the character-creation-flag
	 */
	public void toggleCharacterCreation()
	{
		if (isCharacterCreationEnabled)
		{
			isCharacterCreationEnabled = false;
		}
		else
		{
			isCharacterCreationEnabled = true;
		}
	}
}
