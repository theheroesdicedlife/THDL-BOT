package thdl.lib.factories.rpg;


import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import thdl.lib.collections.rpg.TaleCollection;
import thdl.lib.discord.ThdlMember;
import thdl.lib.rpg.Tale;


public class TaleFactory
{

	private static TaleCollection taleC = new TaleCollection();

	/**
	 * Return the Tale for the TextChannel or null
	 * 
	 * @param text
	 * @return
	 */
	public static Tale getTale(TextChannel text)
	{
		Tale t = null;
		if (taleC.containsChannel(text))
		{
			t = taleC.getTaleByChannel(text);
		}
		return t;
	}

	/**
	 * Creates a Tale if it is not yet created.
	 * 
	 * @param taleName
	 *            Name of the new Tale
	 * @param storyteller
	 *            ThdlMember, who has the storyteller role
	 * @param taleRole
	 *            Role created for this Tale
	 * @param mainChannel
	 *            TextChannel created for this Tale
	 * @param secondaryChannel
	 *            VoiceChannel created for this Tale
	 * @return
	 */
	public static Tale createTale(String taleName, ThdlMember storyteller, Role taleRole, TextChannel mainChannel,
			VoiceChannel secondaryChannel)
	{
		Tale t = new Tale(taleName, storyteller, taleRole, mainChannel, secondaryChannel);
		if (taleC.addTale(t))
		{
			return t;
		}
		else
		{
			return taleC.getTaleByChannel(mainChannel);
		}
	}

	public static boolean isNameInUse(String taleName)
	{
		return taleC.containsName(taleName);
	}
}
