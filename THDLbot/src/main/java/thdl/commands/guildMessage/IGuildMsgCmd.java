package thdl.commands.guildMessage;


public interface IGuildMsgCmd
{

	// Error - Messages
	public static String	ERROR_NO_MEMBER			= "The Author of this command is not registered";
	public static String	ERROR_UNAUTHORIZED		= "You are not a Storyteller";
	public static String	ERROR_FALSE_QUANTITY	= "Quantity is not working :frowning:";

	// Info - Messages
	public static String	INFO_TALE_NAME_IN_USE	= "I'm sorry. The name for your tale is already in use";
	public static String	INFO_FORMAT_CREATE_TALE	= "You should use the format -createTale [talename]";
	public static String	INFO_FORMAT_DICE_FOUR	= "Please use the format -w4 [quantity] :weary:";

	// Success - Messages
	public static String	SUC_PNP_CREATED_FIRST	= "The THDL-based PnP ";
	public static String	SUC_PNP_CREATED_AFTER	= " was created!";
	public static String	SUC_DICE_FOUR_THROW		= " throws a 4 sided dice ";
	public static String	SUC_DICE_TIMES			= " times ";
	public static String	SUC_DICE_GETS_A			= "and gets a ";

	// Exception - Messages
	public static String EXC_NUMBER_FORMAT = "Quantity is not a number";
}
