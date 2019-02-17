package thdl.bot;


public interface ILogMain
{

	// log
	public static String	NAME	= "Main-Log";
	public static String	NUM		= "MAIN";

	// log-Object
	public static String	COMMAND_HANLDER		= "CommandHandler";
	public static String	DIRECT_MSG_HANDLER	= "DirectMessageHandler";
	public static String	THDL_MEMBER_F		= "ThdlMemberFactory";
	public static String	TXT_CH_F			= "TextChannelFactory";

	// log-cause
	public static String	COMMAND				= "Command used";
	public static String	DIRECT_COMMAND		= "Private command used";
	public static String	COMMAND_UNKNOWN		= "Command is unknown";
	public static String	DB_CONNECT			= "Database connection";
	public static String	DB_RECONNECT		= "Database reconnect";
	public static String	DB_CONNECT_CLOSE	= "Database connection close";
	public static String	NO_ADMIN_PERM		= "Bot has no Admin permission";
	public static String	CMD_HANDLE_EXC		= "Command handling exception";
	public static String	DM_HANDLE_EXC		= "Direct message handling exception";
}
