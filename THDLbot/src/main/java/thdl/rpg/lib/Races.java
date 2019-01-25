package thdl.rpg.lib;

import java.util.ArrayList;

public abstract class Races
{
	private static ArrayList<String>	racesOfTHDL		= new ArrayList<>();
	private static final String[]		standardRaces	= { "Human", "Elf", "Orc", "Dwarf", "Halfelf", "Dex-type-Demi",
	        "Str-type-Demi", "Psy-type-Demi" };

	public static ArrayList<String> getRacesOfTHDL()
	{
		return racesOfTHDL;
	}

	public static String[] getStandardRaces()
	{
		return standardRaces;
	}
}
