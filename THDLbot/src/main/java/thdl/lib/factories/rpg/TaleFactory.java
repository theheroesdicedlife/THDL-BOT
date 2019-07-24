package thdl.lib.factories.rpg;


import java.sql.SQLException;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import thdl.lib.collections.rpg.TaleCollection;
import thdl.lib.discord.ThdlMember;
import thdl.lib.rpg.Tale;


public class TaleFactory
{

	private static TaleFactory instance = null;

	private TaleCollection taleC = null;

	private TaleFactory()
	{
		taleC = new TaleCollection();
	}

	public static TaleFactory getInstance()
	{
		if (instance == null)
		{
			instance = new TaleFactory();
		}
		return instance;
	}

	/**
	 * Return the Tale for the TextChannel or null
	 * 
	 * @param text
	 * @return
	 */
	public Tale getTale(TextChannel text)
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
	 *            is the Name of the new Tale
	 * @param storyteller
	 *            is the ThdlMember, who has the storyteller role
	 * @param taleRole
	 *            is the Role created for this Tale
	 * @param mainChannel
	 *            is the TextChannel created for this Tale
	 * @param secondaryChannel
	 *            is the VoiceChannel created for this Tale
	 * @return the new created Tale or the tale which is already in the collection
	 * @throws SQLException
	 */
	public Boolean createTale(String taleName, ThdlMember storyteller, Role taleRole, TextChannel mainChannel,
			VoiceChannel secondaryChannel) throws SQLException
	{
		Tale t = new Tale(taleName, storyteller, taleRole, mainChannel, secondaryChannel);
		// TaleUpdate update = TaleUpdate.getInstance();
		if (taleC.addTale(t))
		{
			// update.addNewTale(t);
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean isNameInUse(String taleName)
	{
		return taleC.containsName(taleName);
	}
}
