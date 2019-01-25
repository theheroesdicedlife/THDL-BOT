package thdl.rpg.lib;

import java.util.ArrayList;
import net.dv8tion.jda.core.entities.User;

public class PlayerCharacter extends RpgCharacter
{
	private String				name			= "";
	private Player				owner			= null;
	private User				creator			= null;
	private String				myRace			= "";
	private String				myJob			= "";
	private int					politicalStand	= 0;
	private int					influence		= 0;
	private int					age				= 0;
	private int[][]				attribute		= null;
	private ArrayList<String>	abilitySet		= null;
	private ArrayList<String>	inventory		= null;

	public PlayerCharacter(String name, String race, User creator)
	{
		this.name = name;
		this.myRace = race;
		this.creator = creator;
		attribute = new int[9][2];
		abilitySet = new ArrayList<>();
		inventory = new ArrayList<>();
	}

	public void addAbility(String ability)
	{
		abilitySet.add(ability);
	}

	public void addItem(String item)
	{
		inventory.add(item);
	}

	public void addAttribute(int name, int value, int exp)
	{
		attribute[name][0] = value;
		attribute[name][1] = exp;
	}

	public void setJob(String job)
	{
		this.myJob = job;
	}

	public void setOwner(Player owner)
	{
		this.owner = owner;
	}

	public ArrayList<String> getAbilitySet()
	{
		return abilitySet;
	}

	public String getName()
	{
		return name;
	}

	public User getCreator()
	{
		return creator;
	}

	public ArrayList<String> getInventory()
	{
		return inventory;
	}

	public Player getOwner()
	{
		return owner;
	}

	public String getMyRace()
	{
		return myRace;
	}

	public String getMyJob()
	{
		return myJob;
	}

	public int getPoliticalStand()
	{
		return politicalStand;
	}

	public int getAge()
	{
		return age;
	}

	public int getInfluence()
	{
		return influence;
	}

	public void setInfluence(int influence)
	{
		this.influence = influence;
	}

	public int[][] getAttribute()
	{
		return attribute;
	}

	public void setMyJob(String myJob)
	{
		this.myJob = myJob;
	}

	public void setPoliticalStand(int politicalStand)
	{
		this.politicalStand = politicalStand;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

}
