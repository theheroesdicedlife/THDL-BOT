package thdl.update.rpg;


public interface IStaticSQL
{

	// SQL
	public static String	INSERT		= "INSERT INTO";
	public static String	SELECT		= "SELECT";
	public static String	FROM		= "FROM";
	public static String	DELETE		= "DELETE";
	public static String	UPDATE		= "UPDATE TABLE";
	public static String	WHERE		= "WHERE";
	public static String	AND			= "AND";
	public static String	VALUES		= "VALUES";
	public static String	SET			= "SET";
	public static String	SPACE		= " ";
	public static String	EQUAL		= "=";
	public static String	BRK_OPEN	= "(";
	public static String	BRK_CLOSE	= ")";
	public static String	COMMA		= ",";

	// table_names
	public static String	TALE		= "tale";
	public static String	TALE_RACE	= "TaleRace";

	// column_names
	public static String	NAME		= "Name";
	public static String	TALE_COL	= "Tale";

	// tale_column_names
	public static String	TELLER			= "StorytellerID";
	public static String	ROLE			= "RoleID";
	public static String	TXT_CHANNEL		= "TextChannelID";
	public static String	VC_CHANNEL		= "VoiceChannelID";
	public static String	START_JOB_ACT	= "StartJobActive";
	public static String	DET_OF_EST		= "DetermOfEstate";
	public static String	DET_OF_AFF		= "DetermOFAffinity";
	public static String	IS_STARTED		= "IsStarted";

}
