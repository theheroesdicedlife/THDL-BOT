package thdl.commands.directMessage;


public interface IDirectMsgCmd
{

	// Error-Msg
	public static final String	NO_PERMISSION		= "You can't use this command with your rank";
	public static final String	NOT_A_MEMBER		= "I'm sorry but it seems like you aren't a member for THDL";
	public static final String	NOT_INVITED			= "You're not invited to ";
	public static final String	ALREADY_PLAYER		= "You have already accepted the invite for ";
	public static final String	TALE_STARTED		= "The tale is started. You have to wait until its paused.";
	public static final String	UNEXPECTED_ERROR	= "An unexpected error as occured. Please contact a bot-Administrator";

	// Info-Msg
	public static final String	SHOW_LOG			= "I show you the log";
	public static final String	PATTERN_SHOW_LOG	= "Pattern of command: showLog";
	public static final String	PATTERN_ACCEPT		= "You need to use the pattern: accept [what] ([other arguments])";
	public static final String	PATTERN_ACC_SPEC	= "You need to use the pattern: accept ";
	public static final String	PATTERN_ACC_DEC_INV	= "invite [talename]";
	public static final String	ACC_DEC_TYPES		= "You can accept or decline: invite, trade";

	// Success-Msg

	// Exception-Msg

	// Accept-Decline-Pattern
	public static final String INVITE = "invite";

}
