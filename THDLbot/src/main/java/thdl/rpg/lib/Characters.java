package thdl.rpg.lib;

import java.util.ArrayList;

public class Characters
{
	private static ArrayList<RpgCharacter> allCharacter = new ArrayList<>();

	public static void addCharacter(RpgCharacter rpgc)
	{
		allCharacter.add(rpgc);
	}

}
