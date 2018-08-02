package rpglib;

import java.util.HashMap;

public class Turn implements Stats
{
	private Player					myPlayer	= null;
	private RpgCharacter			myCharacter	= null;
	private int[]					diceResult	= null;
	private HashMap<String, Bonus>	bonusList	= null;

	public Turn(Player p)
	{
		myPlayer = p;
		myCharacter = p.getMyCharacter();
		initialize();
	}

	public Turn(NonPlayerCharacter npc)
	{
		myCharacter = npc;
		initialize();
	}

	private void initialize()
	{
		diceResult = new int[2];
		diceResult[0] = -1;
		diceResult[1] = -1;
	}

	public void addBonus(int value, String stat, String cause, int time)
	{
		int index = 0;
		for (int i = 0; i < STATNAMES.length; i++)
		{
			if (STATNAMES[i].equals(stat))
			{
				index = i;
			}
		}

		if (!bonusList.containsKey(cause))
		{
			Bonus b = new Bonus(value, index, cause, time);
			bonusList.put(cause, b);
		}
	}

	public Bonus removeBonus(String cause)
	{
		Bonus aBonus = bonusList.get(cause);

		bonusList.remove(cause);

		return aBonus;
	}

	public boolean isBonusExisting(String cause)
	{
		return bonusList.containsKey(cause);
	}

	public boolean secureResultOfDice(int result)
	{
		if (diceResult[0] == -1)
		{
			diceResult[0] = result;
			return true;
		}
		else
			if (diceResult[1] == -1)
			{
				diceResult[1] = result;
				return true;
			}
			else
			{
				return false;
			}
	}

	public Player getMyPlayer()
	{
		return myPlayer;
	}

	public int[] getDiceResult()
	{
		return diceResult;
	}

	public void setMyPlayer(Player myPlayer)
	{
		this.myPlayer = myPlayer;
	}

	public RpgCharacter getMyCharacter()
	{
		return myCharacter;
	}

	public void setMyCharacter(RpgCharacter myCharacter)
	{
		this.myCharacter = myCharacter;
	}

}
