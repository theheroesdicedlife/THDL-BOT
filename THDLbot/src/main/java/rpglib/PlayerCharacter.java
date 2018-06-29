package rpglib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import thdl.bot.DiscordWriter;

public class PlayerCharacter extends RpgCharacter implements Stats
{
	// PC: hashmap<String, Job> job over name; arraylist<itemnames>; player;
	private HashMap<String, Job>	jobsOfCharacter		= null;
	private Player					player				= null;
	private ArrayList<String>		itemsOfCharacter	= null;

	public PlayerCharacter(String name, int age, Player player)
	{
		this.setName(name);
		this.setAge(age);
		this.player = player;

		String talename = this.player.getMyTale().getName();
		String characterID = talename + "-" + this.getName();
		this.setCharacterID(characterID);

		jobsOfCharacter = new HashMap<>();
		this.setStats(new int[9][2]);
		this.setAbilitySet(new HashMap<>());
		itemsOfCharacter = new ArrayList<>();
		this.setWeaponsOfCharacter(new HashMap<>());
	}

	public void addJob(Job j)
	{
		jobsOfCharacter.put(j.getLabel(), j);
	}

	@Override
	public void calculateStats(int ages, DiscordWriter writer)
	{
		int[] randoms = new int[9];
		int[] racest = new int[9];
		int[] jobst = new int[9];

		randomStats(randoms, writer);
		getRace().raceStats(racest, ages);
		jobsOfCharacter.forEach((n1, n2) -> n2.jobStats(jobst));

		int[][] charStats = this.getStats();

		charStats[STR][0] = randoms[STR] + racest[STR] + jobst[STR];
		charStats[VIT][0] = randoms[VIT] + racest[VIT] + jobst[VIT];
		charStats[INT][0] = randoms[INT] + racest[INT] + jobst[INT];
		charStats[PSY][0] = randoms[PSY] + racest[PSY] + jobst[PSY];
		charStats[DEX][0] = randoms[DEX] + racest[DEX] + jobst[DEX];
		charStats[LED][0] = randoms[LED] + racest[LED] + jobst[LED];
		charStats[MOV][0] = randoms[MOV] + racest[MOV] + jobst[MOV];
		charStats[TAB][0] = randoms[TAB] + racest[TAB] + jobst[MOV];
		charStats[ACT][0] = randoms[ACT] + racest[ACT] + jobst[ACT];

		this.setStats(charStats);

		writer.writeInfo("Your caracter has got the following Starting-Stats: ");
		String out = "";
		for (int i = 0; i < charStats.length; i++)
		{
			out = out + STATNAMES[i] + ": " + charStats[i][0] + " ";
		}
		writer.writeInfo(out);
	}

	@Override
	public void createAbilitySet(DiscordWriter writer)
	{
		ArrayList<String> raceAbilities = new ArrayList<>();
		ArrayList<String> jobAbilities = new ArrayList<>();
		// ArrayList<String> jobAbToAdd = new ArrayList<>();

		raceAbilities.addAll(getRace().getAbilities());

		writer.writeInfo("Your abilities are:");

		jobsOfCharacter.forEach((jname, job) ->
			{

				for (String ability1 : job.getAbilities())
				{

					if (!jobAbilities.contains(ability1))
					{
						jobAbilities.add(ability1);
					}
					/*
					 * for (String ability2 : jobAbilities)
					 * {
					 * if (ability1.compareTo(ability2) != 0)
					 * {
					 * jobAbToAdd.add(ability1);
					 * }
					 * }
					 */
				}
				// jobAbilities.addAll(jobAbToAdd);
			});

		String out = "";

		for (String ability : raceAbilities)
		{
			if (!getAbilitySet().containsKey(ability))
			{
				getAbilitySet().put(ability, Ability.getAbility(ability));
				out = out + ability + "   ";
			}
		}

		for (String ability : jobAbilities)
		{
			if (!getAbilitySet().containsKey(ability))
			{
				getAbilitySet().put(ability, Ability.getAbility(ability));
				out = out + ability + "   ";
			}
		}

		writer.writeInfo(out);
	}

	public void createInventory(DiscordWriter writer)
	{
		writer.writeInfo("You get the following items: ");
		String out = "";

		jobsOfCharacter.forEach((n1, n2) ->
			{
				itemsOfCharacter.addAll(n2.getItems());
			});

		for (String s : itemsOfCharacter)
		{
			out = out + s + "   ";
		}

		writer.writeInfo(out);
		out = "";

		writer.writeInfo("Your usable weapons are: ");

		jobsOfCharacter.forEach((name, job) ->
			{
				job.getWeapons().forEach((weapon) ->
					{
						if (getWeaponsOfCharacter().containsKey(weapon))
						{
							getWeaponsOfCharacter().put(weapon, WeaponTemplates.createWeapon(weapon));
						}
					});
			});

		Set<String> keyList = getWeaponsOfCharacter().keySet();

		for (String s : keyList)
		{
			out = out + s + "   ";
		}

		writer.writeInfo(out);
	}
}
