package rpglib;

import java.util.HashMap;
import thdl.bot.DiscordWriter;

public abstract class Entity implements Stats
{
	// Entity: name; mana; leben; int[9][2] stats/ Boni/Mali; arraylist<abilitynames> abilityset
	private String						name		= "";
	private int[][]						stats		= null;
	private HashMap<String, Ability>	abilitySet	= null;

	public abstract void calculateStats(int ages, DiscordWriter writer);

	public abstract void createAbilitySet(DiscordWriter writer);

	public void addBonus(int value, String stat)
	{
		int index = -1;
		for (int i = 0; i < STATNAMES.length; i++)
		{
			if (STATNAMES[i].equals(stat))
			{
				index = i;
			}
		}

		stats[index][1] = stats[index][1] + value;
	}

	public void removeBonus(int stat, int value)
	{
		stats[stat][1] = stats[stat][1] - value;
	}

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
