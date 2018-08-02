package rpglib;

import java.util.HashMap;
import java.util.Random;
import thdl.bot.DiscordWriter;

public abstract class RpgCharacter extends Entity
{
	// RPGC: ID = tale+name; race; age; exp; hashmap<string, weapon> weapons over name
	private String					characterID			= "";
	private Race					race				= null;
	private int						age					= 0;
	private HashMap<String, Weapon>	weaponsOfCharacter	= null;

	protected void randomStats(int[] ranst, DiscordWriter writer)
	{
		// TODO:
		Random rand = new Random();
		rand.nextInt();
		int diced = 0;

		// Str
		diced = rand.nextInt(10) + 1;
		ranst[STR] = diced + 10;
		writer.writeInfo("A 10 sided dice was thrown for strength and is shows a " + diced);
		writer.writeInfo("Your basic strength is " + ranst[STR]);
		diced = 0;

		// Vit
		diced = rand.nextInt(20) + 1;
		ranst[VIT] = diced + 15;
		writer.writeInfo("A 20 sided dice was thrown for vitality and is shows a " + diced);
		writer.writeInfo("Your basic vitality is " + ranst[VIT]);
		diced = 0;

		// Int
		diced = rand.nextInt(10) + 1;
		ranst[INT] = diced + 5;
		writer.writeInfo("A 10 sided dice was thrown for intelligence and is shows a " + diced);
		writer.writeInfo("Your basic intelligence is " + ranst[INT]);
		diced = 0;

		// Psy
		diced = rand.nextInt(20) + 1;
		ranst[PSY] = diced + 10;
		writer.writeInfo("A 20 sided dice was thrown for vitality and is shows a " + diced);
		writer.writeInfo("Your basic psyche is " + ranst[PSY]);
		diced = 0;

		// Dex
		diced = rand.nextInt(10) + 1;
		ranst[DEX] = diced + 10;
		writer.writeInfo("A 10 sided dice was thrown for dextery and is shows a " + diced);
		writer.writeInfo("Your basic dextery is " + ranst[DEX]);
		diced = 0;

		// Led
		diced = rand.nextInt(4) + 1;
		ranst[LED] = diced;
		writer.writeInfo("A 4 sided dice was thrown for leadership and is shows a " + diced);
		writer.writeInfo("Your basic leading-ability is " + ranst[LED]);
		diced = 0;

		// Mov
		diced = rand.nextInt(6) + 1;
		ranst[MOV] = diced + 5;
		writer.writeInfo("A 6 sided dice was thrown for movement and is shows a " + diced);
		writer.writeInfo("Your basic movementspeed is " + ranst[MOV]);
		diced = 0;

		// Tab
		diced = rand.nextInt(4) + 1;
		ranst[TAB] = diced;
		writer.writeInfo("A 4 sided dice was thrown for teamability and is shows a " + diced);
		writer.writeInfo("Your basic Teamplay is determint by" + ranst[TAB]);
		diced = 0;

		// Act
		ranst[ACT] = 1;
		writer.writeInfo("As basic actionpoints you have 1");
	}

	protected String getCharacterID()
	{
		return characterID;
	}

	protected void setCharacterID(String characterID)
	{
		this.characterID = characterID;
	}

	protected Race getRace()
	{
		return race;
	}

	public void setRace(Race race)
	{
		this.race = race;
	}

	private int getAge()
	{
		return age;
	}

	protected void setAge(int age)
	{
		this.age = age;
	}

	public HashMap<String, Weapon> getWeaponsOfCharacter()
	{
		return weaponsOfCharacter;
	}

	protected void setWeaponsOfCharacter(HashMap<String, Weapon> weaponOfCharacter)
	{
		this.weaponsOfCharacter = weaponOfCharacter;
	}

}
