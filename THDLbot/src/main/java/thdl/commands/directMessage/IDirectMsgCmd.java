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
	public static final String	PATTERN_DECLINE		= "You need to use the pattern: decline [what] ([other arguments])";
	public static final String	PATTERN_DEC_SPEC	= "You need to use the pattern: decline ";

	// Success-Msg
	public static final String	PLAYER_ADD_TO_TALE	= "You can now play THDL in the Tale ";
	public static final String	SAY_HALLO			= "Go and greet your fellow players in the TextChannel ";
	public static final String	START_WITH_CREATION	= "Also, start your character-creation with the command: createCharacter [name]";
	public static final String	WE_WILL_MEET		= "We will meet again, while you're playing THDL. But even so, I wish you a lot of fun";
	public static final String	SUCH_A_PITY			= "Such a pity, I thought you would join your friends to play THDL. Well, take care then.";

	// Exception-Msg

	// Accept-Decline-Pattern
	public static final String INVITE = "invite";

	// Others
	public static final String NEXT_LINE = "\n";

	// Messages to discord channels
	public static final String	WELCOME			= "Please welcome ";
	public static final String	DECLINED_INV	= "I'm sorry, but this player declined the invite to play THDL: ";

}
