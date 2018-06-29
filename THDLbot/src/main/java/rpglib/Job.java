package rpglib;

import java.util.ArrayList;
import java.util.Random;

public class Job
{
	private String				label			= "";
	private String[]			stats;
	private ArrayList<String>	abilities		= null;
	private ArrayList<String>	randomAbilities	= null;
	private ArrayList<String>	items			= null;
	private ArrayList<String>	weapons			= null;

	public Job()
	{

		stats = new String[9];
		abilities = new ArrayList<>();
		items = new ArrayList<>();
		weapons = new ArrayList<>();
		randomAbilities = new ArrayList<>();
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public void jobStats(int[] jobst)
	{
		for (int j = 0; j < stats.length; j++)
		{

			if (stats[j].contains("w"))
			{
				String[] presplit = stats[j].split("w");

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

				jobst[j] = factor * (rand.nextInt(dice) + 1);
			}
			else
			{
				jobst[j] = jobst[j] + Integer.parseInt(stats[j]);
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

	public ArrayList<String> getItems()
	{
		return items;
	}

	public ArrayList<String> getWeapons()
	{
		return weapons;
	}
}
