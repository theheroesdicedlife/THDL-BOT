package rpglib;

import java.util.HashMap;
import net.dv8tion.jda.core.entities.TextChannel;

public class Tales
{
	public static HashMap<String, Tale> TaleOverTxtID = new HashMap<>();

	public static Tale getTale(TextChannel txt)
	{
		Tale s = null;
		String key = txt.getId();
		if (TaleOverTxtID.containsKey(key))
		{
			s = TaleOverTxtID.get(key);
		}
		return s;
	}
}
