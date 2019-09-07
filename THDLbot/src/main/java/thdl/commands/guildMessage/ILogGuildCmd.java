package thdl.commands.guildMessage;


public interface ILogGuildCmd
{

	// Log
	public static final String	NAME	= "GUILD_CMD-Log";
	public static final String	NUM		= "LOG_GUILD_CMD";

	// Log-Cause
	public static final String	NO_MEMBER				= "Unregistered author";
	public static final String	NAME_IN_USE				= "Name is in use";
	public static final String	UNAUTHORIZED_USE		= "Usage by Unauthorized";
	public static final String	WRONG_FORMAT			= "Wrong format was used";
	public static final String	CREATE_TALE_EXCEPTION	= "Exception in createTale";
	public static final String	ROLE_CREATE				= "Role created";
	public static final String	CHANNEL_CREATE			= "Channel created";
	public static final String	NO_ROLE					= "No Role found";
	public static final String	NO_CHANNEL				= "No Channel found";
	public static final String	CMD_EXE					= "Command executed";
	public static final String	NUMBER_TRY				= "Not a number";
	public static final String	QUANTITIY_ERROR			= "Quantity is zero or out of range";
	public static final String	OPEN_DM_CHANNEL			= "Open a direct message channel";
	public static final String	NO_TALE_FOUND			= "Channel has no Tale";
	public static final String	ALREADY_PLAYERS_JOINED	= "Already Players in Tale";
	public static final String	NOT_A_THDL_RACE			= "Not the name of a THDL-Race";
	public static final String	DB_UPDATE_FAILED		= "Update to database failed";
	public static final String	TALE_STARTED			= "The Tale is started";
	public static final String	NOT_A_MEMBER			= " is not a member";
	public static final String	CANT_PLAY				= " is not authorized to play";
	public static final String	INVITED					= " was invited to play ";

	// Log - Messages
	public static final String	CMD_CREATE_PNP_SUCCESS			= "Command createPnP was executed with success";
	public static final String	CMD_CREATE_PNP_FAILED			= "Command createPnP could not be executed with success";
	public static final String	CMD_DICE_FOUR_SUCCESS			= "Command -d4 was executed with success";
	public static final String	CMD_DICE_FOUR_FAILED			= "Command -d4 could not be executed with success";
	public static final String	CMD_DICE_SIX_SUCCESS			= "Command -d6 was executed with success";
	public static final String	CMD_DICE_SIX_FAILED				= "Command -d6 could not be executed with success";
	public static final String	CMD_DICE_EIGHT_SUCCESS			= "Command -d8 was executed with success";
	public static final String	CMD_DICE_EIGHT_FAILED			= "Command -d8 could not be executed with success";
	public static final String	CMD_DICE_TEN_SUCCESS			= "Command -d10 was executed with success";
	public static final String	CMD_DICE_TEN_FAILED				= "Command -d10 could not be executed with success";
	public static final String	CMD_DICE_TWELVE_SUCCESS			= "Command -d12 was executed with success";
	public static final String	CMD_DICE_TWELVE_FAILED			= "Command -d12 could not be executed with success";
	public static final String	CMD_DICE_TWENTY_SUCCESS			= "Command -d20 was executed with success";
	public static final String	CMD_DICE_TWENTY_FAILED			= "Command -d20 could not be executed with success";
	public static final String	CMD_DICE_HUNDRED_SUCCESS		= "Command -d100 was executed with success";
	public static final String	CMD_DICE_HUNDRED_FAILED			= "Command -d100 could not be executed with success";
	public static final String	CMD_ADD_RACE_SUCCESS			= "Command -addRace was executed with success";
	public static final String	CMD_ADD_RACE_FAILED				= "Command -addRace could not be executed with success";
	public static final String	CMD_INVITE_SUCCESS				= "Command -invite was executed with success";
	public static final String	CMD_INVITE_FAILED				= "Command -invite could not be executed with success";
	public static final String	DICED							= "Diced: ";
	public static final String	WRONG_PATTERN_CMD				= "User has used wrong format for this command";
	public static final String	ERROR_NOT_A_TALE_CHANNEL		= "The channel you are using is not the channel of a Tale";
	public static final String	ERROR_NOT_AUTHORIZED_IN_TALE	= "Only the Storyteller of this tale can use this command";
}
