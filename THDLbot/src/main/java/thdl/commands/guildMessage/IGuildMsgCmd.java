package thdl.commands.guildMessage;


public interface IGuildMsgCmd
{

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
	public static final String	INFO_FORMAT_ADD_ROLES		= "You should use the format -addRace [racename] ([racename] ...)";
	public static final String	INFO_PLAYERS_HAVE_JOINED	= "You can't change the tale settings after a player has joined";
	public static final String	INFO_STANDARD_RACE_NAMES	= "The names of the standard THDL-Races are: Human, Elf, Dwarf, Orc, Halfelf, Str-Type-Demi, Psy-Type-Demi, Dex-Type-Demi";
	public static final String	INFO_ADVANCED_RACE_NAMES	= "The names of the advanced THDL-Raceset are: Oger, Gnome, Fallen Angel, Angel, Oni, Humanoid Slime, Liszardman, Goblin";

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

	// Exception - Messages
	public static final String EXC_NUMBER_FORMAT = "Quantity is not a number";
}
