package rpglib;

import java.util.ArrayList;
import java.util.Random;

public class Race implements RpgStatic
{
	private String				raceName		= null;
	private int[]				possibleAge		= null;

	private String[][]			stats			= null;
	private ArrayList<String>	abilities		= null;
	private ArrayList<String>	randomAbilities	= null;

	public Race()
	{
		possibleAge = new int[4];
		stats = new String[9][3];
		abilities = new ArrayList<>();
		randomAbilities = new ArrayList<>();
	}

	public void raceStats(int[] racest, int ages)
	{
		for (int j = 0; j < stats.length; j++)
		{

			if (stats[j][ages].contains("w"))
			{
				String[] presplit = stats[j][ages].split("w");

				int factor = 1;
				int dice = 0;
				Random rand = new Random();

				try
				{
					if (presplit[0].contains("*"))
					{
						String[] secsplit = presplit[0].split("*");

						dice = Integer.parseInt(presplit[1]);
						factor = Integer.parseInt(secsplit[0]);
					}
					else
					{
						dice = Integer.parseInt(presplit[0]);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				racest[j] = factor * (rand.nextInt(dice) + 1);
			}
			else
			{
				racest[j] = Integer.parseInt(stats[j][ages]);
			}
		}
	}

	public ArrayList<String> getAbilities()
	{
		ArrayList<String> passedAbilities = new ArrayList<>();
		passedAbilities.addAll(abilities);

		Random r = new Random();
		int i = randomAbilities.size();

		if (i > 0)
		{
			passedAbilities.add(randomAbilities.get(r.nextInt(i)));
		}
		return passedAbilities;
	}

	public int isAgeOk(String test)
	{
		int age = 0;

		try
		{
			age = Integer.parseInt(test);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if (age >= possibleAge[0] && age < possibleAge[1])
		{
			return YOUNG;
		}
		else
			if (age >= possibleAge[1] && age < possibleAge[2])
			{
				return MIDAGED;
			}
			else
				if (age >= possibleAge[2] && age < possibleAge[3])
				{
					return OLD;
				}
				else
				{
					return OUT_OF_AGE_RANGE;
				}

	}

	public String[][] getStats()
	{
		return stats;
	}

	public void setStats(String[][] stats)
	{
		this.stats = stats;
	}
}
