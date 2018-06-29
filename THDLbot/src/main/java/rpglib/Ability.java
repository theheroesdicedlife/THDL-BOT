package rpglib;

import java.util.HashMap;

public abstract class Ability
{
	private static HashMap<String, Ability>	allAbilities		= null;
	private static HashMap<String, Ability>	offensiveAbilities	= null;
	private static HashMap<String, Ability>	defensiveAbilities	= null;
	private static HashMap<String, Ability>	spells				= null;
	private static HashMap<String, Ability>	buffs				= null;
	private static HashMap<String, Ability>	craftingSkills		= null;

	public static Ability getAbility(String name)
	{
		return allAbilities.get(name);
	}
}
