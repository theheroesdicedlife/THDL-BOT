package thdl.bot;


public interface IMainUtil
{

	/**
	 * Interface for static text elements
	 */

	// before-start-commands
	public static final String	PNP_CREATION_CMD		= "createPnP";
	public static final String	ADD_RACES_TO_PNP		= "addRace";
	public static final String	CHARACTER_CREATION_CMD	= "createCharacter";
	public static final String	START_THE_PNP_CMD		= "start";
	public static final String	INVITE_PLAYERS			= "invite";

	// Error Messages
	public static final String	NOT_IN_CMD_TABLE		= "Not in the command-table";
	public static final String	NO_GUILD				= "No guild set";
	public static final String	NO_ADMIN_PERMISSION_BOT	= "The bot has no Administrator permission";
	public static final String	NOT_A_DM_CMD			= "The text you send is not a command";

	// Information Messages
	public static final String GUILD_MAPPING_FIN = "Map finished";

}
