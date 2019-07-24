package thdl.lib.factories.rpg;


import java.util.ArrayList;
import java.util.HashMap;
import thdl.lib.rpg.Race;


public class RaceFactory
{

	private static RaceFactory instance = null;

	private HashMap<String, Race>	raceC		= null;
	private ArrayList<String>		standard	= null;

	private RaceFactory()
	{
		raceC = new HashMap<String, Race>();
		standard = new ArrayList<String>();

		standard.add("Human");
		standard.add("Elf");
		standard.add("Dwarf");
		standard.add("Orc");
		standard.add("Halfelf");
		standard.add("Str-Demi");
		standard.add("Dex-Demi");
		standard.add("Psy-Demi");
	}

	public static RaceFactory getInstance()
	{
		if (instance == null)
		{
			instance = new RaceFactory();
		}
		return instance;
	}

	/**
	 * 
	 * @param name
	 *            the name of a THDL-Race
	 * @return Race
	 *         or null if the race is not in the collection
	 */
	public Race getRace(String name)
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
	public boolean areRaces(String... names)
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
	public void addStandardToTale(ArrayList<String> taleRaces)
	{
		taleRaces.addAll(standard);
	}

	/**
	 * TODO: Usage with db-connection
	 */
	public void setThdlRaces()
	{
		raceC.clear();
		createRace();
	}

	/**
	 * TODO: Usage with setThdlRaces
	 * 
	 * @return
	 */
	private Race createRace()
	{

		return new Race();
	}

}
