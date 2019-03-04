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

	// Log-Message
	public final static String	DMC_PRINTLOGS_SUCCESS	= "The command showLog was executed with success";
	public final static String	DMC_PRINTLOGS_FAIL		= "The command showLog could not be executed with success";
}
