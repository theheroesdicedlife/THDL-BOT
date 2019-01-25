package thdl.factories;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import thdl.bot.DatabaseConnect;
import thdl.bot.DiscordWriter;
import thdl.bot.Loader;
import thdl.rpg.lib.Player;
import thdl.rpg.lib.PlayerCharacter;
import thdl.rpg.lib.Tale;
import thdl.rpg.util.RpgStatic;
import thdl.rpg.util.Stats;

public class CharacterCreator implements RpgStatic, Stats
{
	private String						name	= "";
	private String						race	= "";
	private String						job		= "";
	private GuildMessageReceivedEvent	event	= null;
	private Player						owner	= null;
	private Tale						tale	= null;
	private int							age		= 0;

	public CharacterCreator(String name, String race, String job, GuildMessageReceivedEvent e, Player owner, Tale tale)
	{
		this.name = name;
		this.race = race;
		this.job = job;
		this.event = e;
		this.owner = owner;
		this.tale = tale;
	}

	public PlayerCharacter createCharacter(DiscordWriter writer) throws Exception
	{
		PlayerCharacter aPChara = new PlayerCharacter(name, race, event.getAuthor());

		aPChara.setJob(job);
		aPChara.setOwner(owner);

		calculateStats(writer, aPChara);

		createAbilitySet(aPChara);

		if (tale.isAreJobsEnabled())
		{
			createInventory(aPChara);
		}

		safeNewCharcter(aPChara);

		return aPChara;
	}

	private void calculateStats(DiscordWriter writer, PlayerCharacter chara) throws Exception
	{
		Random rand = new Random();
		Loader loader = Loader.getInstance();
		int ages = 0;

		ages = getAge(rand, loader, chara);

		writer.writeSuccess("Your character is " + chara.getAge() + " years old!");
		writer.writeSuccess("Within the " + chara.getMyRace() + " your character is " + AGES[ages]);

		int[] raceAttr = new int[9];
		int[] jobAttr = new int[9];
		int[] charaAttr = new int[9];

		getRaceAttributes(raceAttr, AGES[ages], loader);
		raceAttr[8] = 0;
		if (chara.getMyJob() != null)
		{
			getJobAttributes(rand, loader, jobAttr);
			jobAttr[8] = 0;
		}

		diceCharacterAttributes(rand, writer, charaAttr);

		if (!job.equals(""))
		{
			for (int i = 0; i < chara.getAttribute().length; i++)
			{
				chara.addAttribute(i, charaAttr[i] + raceAttr[i] + jobAttr[i], 0);
			}
		}
		else
		{
			for (int i = 0; i < chara.getAttribute().length; i++)
			{
				chara.addAttribute(i, charaAttr[i] + raceAttr[i], 0);
			}
		}

		if (tale.getSettingPoliticalStand() == RANDOM_STAND)
		{
			setPolitical(rand, writer, chara);
		}
		else
		{
			setPolitical(writer, chara);
		}

	}

	private void setPolitical(DiscordWriter writer, PlayerCharacter chara)
	{
		chara.setPoliticalStand(WORKER);
		chara.setInfluence(0);
		writer.writeInfo("You have 0 influence, hence your character is a simple worker right now!");
	}

	private void setPolitical(Random rand, DiscordWriter writer, PlayerCharacter chara)
	{
		int influence = rand.nextInt(160) - 10;
		chara.setInfluence(influence);
		writer.writeInfo("Now I have diced your influence, you got " + chara.getInfluence());

		int politicalStand = (influence >= -10 && influence < 0) ? BEGGAR
		        : (influence >= 0 && influence < 30) ? WORKER
		                : (influence >= 30 && influence < 60) ? CITIZEN
		                        : (influence >= 60 && influence < 75) ? ARISTOCRATE
		                                : (influence >= 75 && influence < 85) ? BARON
		                                        : (influence >= 85 && influence < 100) ? LORD
		                                                : (influence >= 100 && influence < 150) ? PRINCE
		                                                        : (influence >= 150) ? KING : 0;

		chara.setPoliticalStand(politicalStand);
		writer.writeInfo("Your character is a " + POLITICS[politicalStand] + "!");
	}

	private void diceCharacterAttributes(Random rand, DiscordWriter writer, int[] attr)
	{
		String sep = System.getProperty("line.separator");

		String out = "";

		attr[STR] = 10 + rand.nextInt(10) + 1;
		out = out + String.format("I have thrown a dice for your character's strength! %s The result is %d! %s", sep,
		        attr[STR], sep);

		attr[VIT] = 15 + rand.nextInt(20) + 1;
		out = out + String.format("I did this for your character's vitality, too! %s The result is %d! %s", sep,
		        attr[VIT], sep);

		attr[INT] = 5 + rand.nextInt(10) + 1;
		out = out + String.format("And also, for your character's intelligence! %s The result is %d! %s", sep,
		        attr[INT], sep);

		attr[PSY] = 10 + rand.nextInt(20) + 1;
		out = out + String.format("Futhermore I threw a dice for your character's psyche! %s The result is %d! %s", sep,
		        attr[PSY], sep);

		attr[DEX] = 10 + rand.nextInt(10) + 1;
		out = out + String.format("And for your character's dexterity! %s The result is %d! %s", sep, attr[DEX], sep);

		attr[LED] = rand.nextInt(4) + 1;
		out = out + String.format("Your character's leadership was thrown by I! %s The result is %d! %s", sep,
		        attr[LED], sep);

		attr[MOV] = 5 + rand.nextInt(6) + 1;
		out = out + String.format("Wow! You got initiative! I went and threw a dice! %s The result is %d! %s", sep,
		        attr[MOV], sep);

		attr[TAB] = rand.nextInt(4) + 1;
		out = out + String.format("I got to throw a dice for your character's teamability! %s The result is %d! %s",
		        sep, attr[TAB], sep);

		attr[ACT] = 2;
		out = out + "You also got 2 actionpoints";

		writer.writeSuccess(out);

	}

	private void getRaceAttributes(int[] attribute, String ages, Loader loader) throws Exception
	{
		String[] attributeString = new String[8];

		loader.loadRaceAttributes(race, attributeString, ages);

		for (int i = 0; i < attributeString.length; i++)
		{
			attribute[i] = Integer.parseInt(attributeString[i]);
		}
	}

	private void getJobAttributes(Random rand, Loader loader, int[] jobAttr) throws Exception
	{
		String[] help = new String[9];
		loader.loadJobAttributes(help, job);

		// 1+1*d4

		for (int i = 0; i < help.length - 1; i++)
		{
			int adder = 0;
			int multi = 0;
			int random = 0;
			String attr = help[i];

			if (attr.contains("*d"))
			{
				if (attr.contains("+"))
				{
					String[] helpAdd = attr.split("\\+");
					adder = Integer.parseInt(helpAdd[0]);

					String[] helpMulti = helpAdd[1].split("\\*");
					multi = Integer.parseInt(helpMulti[0]);

					String dice = helpMulti[1];

					for (int j = 0; j <= multi; j++)
					{
						random = random + (dice.equals("d4") ? rand.nextInt(4) + 1
						        : dice.equals("d6") ? rand.nextInt(6) + 1
						                : dice.equals("d8") ? rand.nextInt(8) + 1
						                        : dice.equals("d10") ? rand.nextInt(10) + 1
						                                : dice.equals("d12") ? rand.nextInt(12) + 1
						                                        : dice.equals("d20") ? rand.nextInt(20) + 1 : 0);
					}

					jobAttr[i] = random + adder;
				}

			}
			else
			{
				jobAttr[i] = Integer.parseInt(attr);
			}
		}
	}

	public void createAbilitySet(PlayerCharacter chara) throws SQLException
	{
		ArrayList<String> abilityRace = new ArrayList<>();
		ArrayList<String> dicedRace = new ArrayList<>();
		ArrayList<String> abilityJob = new ArrayList<>();
		ArrayList<String> dicedJob = new ArrayList<>();
		Loader loader = Loader.getInstance();

		loader.loadRaceAbilities(race, abilityRace, dicedRace);

		abilityRace.forEach((ability) -> chara.addAbility(ability));

		chara.addAbility(randomAbility(dicedRace));

		if (!job.equals(""))
		{
			loader.loadJobAbilities(job, abilityJob, dicedJob);
			for (String skill : abilityJob)
			{
				if (!chara.getAbilitySet().contains(skill))
				{
					chara.addAbility(skill);
				}
			}
			chara.addAbility(randomAbility(dicedJob));
		}
	}

	private String randomAbility(ArrayList<String> abilities)
	{
		String ability = "";
		int area = 0;
		Random rand = new Random();

		if (abilities.size() != 0)
		{
			area = 100 / abilities.size();
		}
		else
		{
			area = 100;
		}

		int rNum = rand.nextInt(99) + 1;

		for (int i = 1; i <= abilities.size(); i++)
		{
			int div = 0;
			div = area * i + 1;

			if (rNum <= div)
			{
				ability = abilities.get(i - 1);
				break;
			}
		}

		return ability;
	}

	public void createInventory(PlayerCharacter chara) throws SQLException
	{
		Loader loader = Loader.getInstance();
		ArrayList<String> items = new ArrayList<>();

		loader.loadJobItems(job, items);

		items.forEach((item) -> chara.addItem(item));
	}

	private int getAge(Random rand, Loader loader, PlayerCharacter chara) throws SQLException
	{
		int help = 0;
		int[] ageBorder = new int[4];

		loader.loadRaceAges(race, ageBorder);

		while (help < ageBorder[0])
		{
			help = rand.nextInt(ageBorder[3]) + 1;
		}

		age = help;
		chara.setAge(age);

		if (age <= ageBorder[1])
		{
			return 0;
		}
		else
			if (age > ageBorder[1] && age < ageBorder[2])
			{
				return 1;
			}
			else
				if (age >= ageBorder[2])
				{
					return 2;
				}
				else
				{
					return 3;
				}
	}

	public void safeNewCharcter(PlayerCharacter chara) throws SQLException
	{
		DatabaseConnect dbc = DatabaseConnect.getInstance();

		String creaID = chara.getCreator().getId();
		String playerID = chara.getOwner().getMySelf().getUser().getId();
		String taleName = chara.getOwner().getMyTale().getName();

		String sql = "";
		sql = sql + "INSERT INTO Tale_Character ";
		sql = sql + "(TCharacter_ID, Character_Name, Creator, Owner, Race, Job, Political, Influence, Age) ";
		sql = sql + "values ";
		sql = sql + "(UUID(), '" + chara.getName() + "', '" + creaID + "', ";
		sql = sql + "(SELECT player_ID FROM Player WHERE user = '" + playerID + "' AND tale = ";
		sql = sql + "(SELECT tale_ID FROM Tale WHERE tale_Name = '" + taleName + "')), '";
		sql = sql + chara.getMyRace() + "', '" + chara.getMyJob() + "', " + chara.getPoliticalStand() + ", ";
		sql = sql + chara.getInfluence() + ", " + chara.getAge() + ")";

		dbc.updateData(sql);

		sql = "";

		sql = sql + "INSERT INTO Chara_Attribute ";
		sql = sql + "(Chara_Attr_ID, Rpg_Character, Attribute, Value, Exp) values ";

		for (int i = 0; i < chara.getAttribute().length - 1; i++)
		{
			sql = sql + "(UUID(), ";
			sql = sql + "(SELECT c.TCharacter_ID FROM Tale_Character c, Player p WHERE c.Character_Name = '" + name
			        + "' ";
			sql = sql + "AND c.owner = p.player_ID AND p.tale = ";
			sql = sql + "(SELECT tale_ID FROM Tale WHERE tale_Name = '" + taleName + "')), ";
			sql = sql + i + ", " + chara.getAttribute()[i][0] + ", " + 0 + "), ";
		}

		sql = sql + "(UUID(), ";
		sql = sql + "(SELECT c.TCharacter_ID FROM Tale_Character c, Player p WHERE c.Character_Name = '" + name + "' ";
		sql = sql + "AND c.owner = p.player_ID AND tale = ";
		sql = sql + "(SELECT tale_ID FROM Tale WHERE tale_Name = '" + taleName + "')), ";
		sql = sql + 8 + ", " + chara.getAttribute()[8][0] + ", " + 0 + ")";

		dbc.updateData(sql);

		sql = "";

		sql = sql + "INSERT INTO Chara_Ability (Chara_Ab_ID, Rpg_Character, Ability) values ";

		int j = 0;
		for (j = 0; j < chara.getAbilitySet().size() - 1; j++)
		{
			String skill = chara.getAbilitySet().get(j);
			sql = sql + "(UUID(), (SELECT c.TCharacter_ID FROM Tale_Character c, Player p ";
			sql = sql + "WHERE c.Character_Name = '" + name + "' AND c.owner = p.player_ID AND p.tale = ";
			sql = sql + "(SELECT tale_ID FROM Tale WHERE tale_Name = '" + taleName + "')), ";
			sql = sql + "(SELECT Ability_ID FROM Ability WHERE Ability_Name = '" + skill + "')), ";
		}

		String skill = chara.getAbilitySet().get(j);
		sql = sql + "(UUID(), (SELECT c.TCharacter_ID FROM Tale_Character c, Player p ";
		sql = sql + "WHERE c.Character_Name = '" + name + "' AND c.owner = p.player_ID AND p.tale = ";
		sql = sql + "(SELECT tale_ID FROM Tale WHERE tale_Name = '" + taleName + "')), ";
		sql = sql + "(SELECT Ability_ID FROM Ability WHERE Ability_Name = '" + skill + "'))";

		dbc.updateData(sql);

		sql = "";

		sql = sql + "INSERT INTO Chara_Inventory (Chara_Inv_ID, Rpg_Character, Item) values ";

		j = 0;
		for (j = 0; j < chara.getInventory().size() - 1; j++)
		{
			String item = chara.getInventory().get(j);
			sql = sql + "(UUID(), (SELECT c.TCharacter_ID FROM Tale_Character c, Player p ";
			sql = sql + "WHERE c.Character_Name = '" + name + "' AND c.owner = p.player_ID AND p.tale = ";
			sql = sql + "(SELECT tale_ID FROM Tale WHERE Tale_Name = '" + taleName + "')), '";
			sql = sql + item + "'), ";
		}

		String item = chara.getInventory().get(j);
		sql = sql + "(UUID(), (SELECT c.TCharacter_ID FROM Tale_Character c, Player p ";
		sql = sql + "WHERE c.Character_Name = '" + name + "' AND c.owner = p.player_ID AND p.tale = ";
		sql = sql + "(SELECT tale_ID FROM Tale WHERE Tale_Name = '" + taleName + "')), '";
		sql = sql + item + "')";

		dbc.updateData(sql);

	}
}
