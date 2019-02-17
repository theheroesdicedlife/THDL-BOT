package thdl.commands.guildMessage;


public interface IGuildMsgCmd
{

	// Error - Messages
	public static String	ERROR_NO_MEMBER			= "The Author of this command is not registered";
	public static String	ERROR_UNAUTHORIZED		= "You are not a Storyteller";
	public static String	ERROR_FALSE_QUANTITY	= "Quantity is not working :frowning:";

	// Info - Messages
	public static String	INFO_TALE_NAME_IN_USE		= "I'm sorry. The name for your tale is already in use";
	public static String	INFO_FORMAT_CREATE_TALE		= "You should use the format -createTale [talename]";
	public static String	INFO_FORMAT_DICE_FOUR		= "Please use the format -d4 [quantity] :weary:";
	public static String	INFO_FORMAT_DICE_SIX		= "Please use the format -d6 [quantity] :weary:";
	public static String	INFO_FORMAT_DICE_EIGHT		= "Please use the format -d8 [quantity] :weary:";
	public static String	INFO_FORMAT_DICE_TEN		= "Please use the format -d10 [quantity] :weary:";
	public static String	INFO_FORMAT_DICE_TWELVE		= "Please use the format -d12 [quantity] :weary:";
	public static String	INFO_FORMAT_DICE_TWENTY		= "Please use the format -d20 [quantity] :weary:";
	public static String	INFO_FORMAT_DICE_HUNDRED	= "Please use the format -d100 [quantity] :weary:";

	// Success - Messages
	public static String	SUC_PNP_CREATED_FIRST	= "The THDL-based PnP ";
	public static String	SUC_PNP_CREATED_AFTER	= " was created!";
	public static String	SUC_DICE_FOUR_THROW		= " throws a 4 sided dice ";
	public static String	SUC_DICE_SIX_THROW		= " throws a 6 sided dice ";
	public static String	SUC_DICE_EIGHT_THROW	= " throws a 8 sided dice ";
	public static String	SUC_DICE_TEN_THROW		= " throws a 10 sided dice ";
	public static String	SUC_DICE_TWELVE_THROW	= " throws a 12 sided dice ";
	public static String	SUC_DICE_TWENTY_THROW	= " throws a 20 sided dice ";
	public static String	SUC_DICE_HUNDRED_THROW	= " throws a 100 sided dice ";
	public static String	SUC_DICE_TIMES			= " times ";
	public static String	SUC_DICE_GETS_A			= "and gets a ";

	// Exception - Messages
	public static String EXC_NUMBER_FORMAT = "Quantity is not a number";
}
