package thdl.commands.guildMessage;


public interface IGuildMsgCmd
{

	// Others:
	public static final String NEXT_LINE = "\n";

	// Error - Messages
	public static final String	ERROR_NO_MEMBER					= "The Author of this command is not registered";
	public static final String	ERROR_UNAUTHORIZED				= "You are not a Storyteller";
	public static final String	ERROR_FALSE_QUANTITY			= "Quantity is not working :frowning:";
	public static final String	ERROR_NOT_A_TALE_CHANNEL		= "The channel you are using is not the channel of a Tale";
	public static final String	ERROR_NOT_AUTHORIZED_IN_TALE	= "Only the Storyteller of this tale can use this command";
	public static final String	ERROR_ONE_RACE_NAME_FALSE		= "At least one of the race-names used, was not a THDL-Racename";

	// Info - Messages
	public static final String	INFO_TALE_NAME_IN_USE		= "I'm sorry. The name for your tale is already in use";
	public static final String	INFO_FORMAT_CREATE_TALE		= "You should use the format -createTale [talename]";
	public static final String	INFO_FORMAT_DICE_FOUR		= "Please use the format -d4 [quantity] :weary:";
	public static final String	INFO_FORMAT_DICE_SIX		= "Please use the format -d6 [quantity] :weary:";
	public static final String	INFO_FORMAT_DICE_EIGHT		= "Please use the format -d8 [quantity] :weary:";
	public static final String	INFO_FORMAT_DICE_TEN		= "Please use the format -d10 [quantity] :weary:";
	public static final String	INFO_FORMAT_DICE_TWELVE		= "Please use the format -d12 [quantity] :weary:";
	public static final String	INFO_FORMAT_DICE_TWENTY		= "Please use the format -d20 [quantity] :weary:";
	public static final String	INFO_FORMAT_DICE_HUNDRED	= "Please use the format -d100 [quantity] :weary:";
	public static final String	INFO_FORMAT_ADD_RACE		= "You should use the format -addRace [racename] ([racename] ...)";
	public static final String	INFO_FORMAT_INVITE_PLAYERS	= "You should use the format -invite [@username] ([@username] ...";
	public static final String	INFO_PLAYERS_HAVE_JOINED	= "You can't change the tale settings after a player has joined";
	public static final String	INFO_STANDARD_RACE_NAMES	= "The names of the standard THDL-Races are: Human, Elf, Dwarf, Orc, Halfelf, Str-Type-Demi, Psy-Type-Demi, Dex-Type-Demi";
	public static final String	INFO_ADVANCED_RACE_NAMES	= "The names of the advanced THDL-Raceset are: Oger, Gnome, Fallen Angel, Angel, Oni, Humanoid Slime, Liszardman, Goblin";
	public static final String	INFO_RACES_ADDED			= "The following races were added to the pnp ";
	public static final String	INFO_TALE_IS_RUNNING		= "I'm sorry, but you can't invite any people as long the tale is running";
	public static final String	INFO_INVITED				= " was invited to the tale";
	public static final String	INFO_COULD_NOT_INVITE		= " couln't be invited, because ";
	public static final String	INFO_NO_INV_CAUSE_NO_MEMBER	= "this is not a member!";
	public static final String	INFO_NO_INV_CAUSE_NOT_AUTH	= "he/she is not a ~Fighter or higher";

	// Success - Messages
	public static final String	SUC_PNP_CREATED_FIRST	= "The THDL-based PnP ";
	public static final String	SUC_PNP_CREATED_AFTER	= " was created!";
	public static final String	SUC_DICE_FOUR_THROW		= " throws a 4 sided dice ";
	public static final String	SUC_DICE_SIX_THROW		= " throws a 6 sided dice ";
	public static final String	SUC_DICE_EIGHT_THROW	= " throws a 8 sided dice ";
	public static final String	SUC_DICE_TEN_THROW		= " throws a 10 sided dice ";
	public static final String	SUC_DICE_TWELVE_THROW	= " throws a 12 sided dice ";
	public static final String	SUC_DICE_TWENTY_THROW	= " throws a 20 sided dice ";
	public static final String	SUC_DICE_HUNDRED_THROW	= " throws a 100 sided dice ";
	public static final String	SUC_DICE_TIMES			= " times ";
	public static final String	SUC_DICE_GETS_A			= "and gets a ";

	// DM - Messages
	public static final String	DM_INV_TO		= "Hello! You were invited to play ";
	public static final String	DM_ANSW_INV		= "Please answer the invitation.";
	public static final String	DM_ACCEPT_INV	= "If you want to join, type 'accept', ";
	public static final String	DM_DECLINE_INV	= "if not, type 'decline' under this message";
}
