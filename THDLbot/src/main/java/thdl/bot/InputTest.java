package thdl.bot;

import thdl.rpg.util.Stats;

public class InputTest implements Stats
{
	private static InputTest singlton = null;

	private InputTest()
	{

	}

	public synchronized static InputTest getInstance()
	{
		if (singlton == null)
		{
			singlton = new InputTest();
		}
		return singlton;
	}

	public boolean testStat(String input)
	{
		boolean isTHDLstat = false;
		int statcount = STATNAMES.length;

		for (int i = 0; i < statcount; i++)
		{
			if (STATNAMES[i].equals(input))
			{
				isTHDLstat = true;
			}
		}

		return isTHDLstat;
	}

	public boolean testEnemyAlly(String[] input)
	{
		boolean isOk = true;
		int i = 1;
		int eCount = 0;
		int aCount = 0;
		while (i < input.length)
		{
			if (!input[i].equals("e") && !input[i].equals("a"))
			{
				isOk = false;
			}

			if (input[i].equals("e"))
			{
				eCount++;
			}
			if (input[i].equals("a"))
			{
				aCount++;
			}

			i = i + 2;
		}

		if (eCount == 0 || aCount == 0)
		{
			isOk = false;
		}
		return isOk;
	}

}
