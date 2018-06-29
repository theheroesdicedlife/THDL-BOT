package rpglib;

import java.util.HashMap;

public abstract class RaceTemplate
{
	private static HashMap<String, Race>	playableRaces	= new HashMap<>();
	private static HashMap<String, Race>	otherRaces		= new HashMap<>();

	public static Race getPlayableRace(String name)
	{
		if (playableRaces.containsKey(name))
		{
			return playableRaces.get(name);
		}
		else
		{
			return null;
		}
	}
}
