package thdl.bot;


public interface IMainUtil
{

	/**
	 * Interface for static text elements, like the dices
	 */
	// Dice-Commands
	public static final String	DICE_D4_CMD		= "d4";
	public static final String	DICE_D6_CMD		= "d6";
	public static final String	DICE_D8_CMD		= "d8";
	public static final String	DICE_D10_CMD	= "d10";
	public static final String	DICE_D12_CMD	= "d12";
	public static final String	DICE_D20_CMD	= "d20";
	public static final String	DICE_D100_CMD	= "d100";
	// before-start-commands
	public static final String	PNP_CREATION_CMD		= "createPnP";
	public static final String	CHARACTER_CREATION_CMD	= "createCharacter";
	public static final String	ADD_PLAYER_CMD			= "addPlayer";
	public static final String	START_THE_PNP_CMD		= "start";
	// Error Messages
	public static final String	NOT_IN_CMD_TABLE		= "Not in the command-table";
	public static final String	NO_GUILD				= "No guild set";
	public static final String	NO_ADMIN_PERMISSION_BOT	= "The bot has no Administrator permission";
	public static final String	NOT_A_DM_CMD			= "The text you send is not a command";

	// Information Messages
	public static final String GUILD_MAPPING_FIN = "Map finished";
}
