package thdl.rpg.lib;

import java.sql.SQLException;
import java.util.HashMap;
import net.dv8tion.jda.core.entities.TextChannel;

public abstract class Tales
{
	private static HashMap<String, Tale>	TaleOverTxtID	= new HashMap<>();
	private static HashMap<String, Tale>	TaleOverName	= new HashMap<>();

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

	public static boolean isNameInUse(String talename)
	{
		return TaleOverName.containsKey(talename);
	}

	public static void addTale(Tale tale)
	{
		TaleOverName.put(tale.getName(), tale);
		TaleOverTxtID.put(tale.getMyText().getId(), tale);
	}

	public static void saveTales()
	{
		TaleOverName.forEach((key, tale) ->
			{
				try
				{
					tale.safe();
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
	}

}
