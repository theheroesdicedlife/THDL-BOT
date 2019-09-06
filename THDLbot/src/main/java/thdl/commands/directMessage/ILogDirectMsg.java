package thdl.commands.directMessage;


public interface ILogDirectMsg
{

	// Log
	public final static String	NUM		= "LOG_DM_CMD";
	public final static String	NAME	= "Direct-Msg-Log";

	// Log-Cause
	public final static String	SHOW_LOGS		= "Logs are shown";
	public final static String	CMD_EXE			= "Direct command executed";
	public final static String	WRONG_PATTERN	= "Used wrong pattern";
	public final static String	NO_PERMISSION	= "User-Rank not high enough";
	public final static String	OPEN_DM_CHANNEL	= "Open a direct message channel";
	public final static String	NOT_INVITED		= "No invite to join the tale";
	public final static String	ALREADY_PLAYER	= "Member is already a player in the tale";
	public final static String	TALE_STARTED	= "Tale is started";
	public final static String	ACCEPT_TYPE		= "Accept-Decline type switch";
	public final static String	PLAYER_ADD		= "Player was added to tale";
	public final static String	PLAYER_DEC		= "Player has declined";

	// Log-Message
	public final static String	DMC_PRINTLOGS_SUCCESS	= "The command showLog was executed with success";
	public final static String	DMC_PRINTLOGS_FAIL		= "The command showLog could not be executed with success";
	public final static String	DMC_ACCEPT_SUCCESS		= "The command accept was executed with success";
	public final static String	DMC_ACCEPT_FAIL			= "The command accept could not be executed with success";
	public final static String	DMC_DECLINE_SUCCESS		= "The command decline was executed with success";
	public final static String	DMC_DECLINE_FAIL		= "The command decline could not be executed with success";
}
