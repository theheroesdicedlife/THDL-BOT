package thdl.bot;


public interface ILogMain
{

	// log
	public final static String	NAME	= "Main-Log";
	public final static String	NUM		= "MAIN";

	// log-Object
	public final static String	COMMAND_HANLDER		= "CommandHandler";
	public final static String	DIRECT_MSG_HANDLER	= "DirectMessageHandler";
	public final static String	THDL_MEMBER_F		= "ThdlMemberFactory";
	public final static String	TXT_CH_F			= "TextChannelFactory";

	// log-cause
	public final static String	COMMAND				= "Command used";
	public final static String	DIRECT_COMMAND		= "Private command used";
	public final static String	COMMAND_UNKNOWN		= "Command is unknown";
	public final static String	DB_CONNECT			= "Database connection";
	public final static String	DB_RECONNECT		= "Database reconnect";
	public final static String	DB_CONNECT_CLOSE	= "Database connection close";
	public final static String	NO_ADMIN_PERM		= "Bot has no Admin permission";
	public final static String	CMD_HANDLE_EXC		= "Command handling exception";
	public final static String	DM_HANDLE_EXC		= "Direct message handling exception";
}
