package thdl.util;


public class IdParser
{

	public final static int CUT_START = 2;

	/**
	 * Parsing the discord-ping in its raw format to an id
	 * 
	 * @param rawUserPing
	 *            is the discord-user-ping as a raw String with the id
	 * @return userId
	 *         is the id of the user who got the ping
	 * 
	 */
	public static String parse(String rawUserPing)
	{
		String userId = "";
		int endIndex = 0;

		if (!rawUserPing.equals(""))
		{
			endIndex = rawUserPing.length() - 1;
			userId = rawUserPing.substring(CUT_START, endIndex);
		}

		return userId;
	}
}
