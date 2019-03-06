package thdl.lib.factories.rpg;


import java.util.ArrayList;
import java.util.HashMap;
import thdl.lib.rpg.Race;


public class RaceFactory
{

	private static HashMap<String, Race>	raceC		= new HashMap<String, Race>();
	private static ArrayList<String>		standard	= new ArrayList<String>();

	/**
	 * 
	 * @param name
	 *            the name of a THDL-Race
	 * @return Race
	 *         or null if the race is not in the collection
	 */
	public static Race getRace(String name)
	{
		if (raceC.containsKey(name))
		{
			return raceC.get(name);
		}
		else
		{
			return null;
		}
	}

	/**
	 * Test for more than one race-name
	 * 
	 * @param names
	 *            The names that have to be tested
	 * @return boolean
	 *         Are all of the names THDL-Racenames
	 */
	public static boolean areRaces(String... names)
	{
		boolean namesOk = false;

		for (String name : names)
		{
			if (raceC.containsKey(name))
			{
				namesOk = true;
			}
			else
			{
				namesOk = false;
				break;
			}
		}

		return namesOk;
	}

	/**
	 * Adds all standard THDL-Races to a tale
	 * 
	 * @param taleRaces
	 *            The ArrayList for racenames of the tale
	 */
	public static void addStandardToTale(ArrayList<String> taleRaces)
	{
		taleRaces.addAll(standard);
	}

	/**
	 * TODO: Usage with db-connection
	 */
	public static void setThdlRaces()
	{
		raceC.clear();
		createRace();
	}

	/**
	 * TODO: Usage with setThdlRaces
	 * 
	 * @return
	 */
	private static Race createRace()
	{
		return new Race();
	}

}
