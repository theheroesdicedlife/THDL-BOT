package rpglib;

import java.util.HashMap;

public abstract class Characters
{
	private static HashMap<String, RpgCharacter> characterOverId = new HashMap<>();

	public static void addCharacter(RpgCharacter rpgc)
	{
		characterOverId.put(rpgc.getCharacterID(), rpgc);
	}
}
