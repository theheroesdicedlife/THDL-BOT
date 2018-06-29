package rpglib;

import java.util.HashMap;
import thdl.bot.DiscordWriter;

public abstract class Entity
{
	// Entity: name; mana; leben; int[9][2] stats/ Boni/Mali; arraylist<abilitynames> abilityset
	private String						name		= "";
	private int[][]						stats		= null;
	private HashMap<String, Ability>	abilitySet	= null;

	public abstract void calculateStats(int ages, DiscordWriter writer);

	public abstract void createAbilitySet(DiscordWriter writer);

	public String getName()
	{
		return name;
	}

	protected void setName(String name)
	{
		this.name = name;
	}

	public int[][] getStats()
	{
		return stats;
	}

	protected void setStats(int[][] stats)
	{
		this.stats = stats;
	}

	protected HashMap<String, Ability> getAbilitySet()
	{
		return abilitySet;
	}

	protected void setAbilitySet(HashMap<String, Ability> abilitySet)
	{
		this.abilitySet = abilitySet;
	}
}
