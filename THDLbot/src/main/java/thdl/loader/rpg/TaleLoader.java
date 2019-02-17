package thdl.loader.rpg;


import java.sql.ResultSet;
import java.sql.SQLException;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import thdl.bot.DatabaseConnect;
import thdl.discord.ThdlMember;
import thdl.factories.discord.RoleFactory;
import thdl.factories.discord.TextChannelFactory;
import thdl.factories.discord.ThdlMemberFactory;
import thdl.factories.discord.VoiceChannelFactory;
import thdl.factories.rpg.TaleFactory;


public class TaleLoader
{

	private static DatabaseConnect dbc = null;

	/**
	 * Loads all Tales from the DB
	 * TODO: SQL
	 * 
	 * @throws SQLException
	 */
	public static void loadTalesFromDB() throws SQLException
	{
		String sqlCmd = "";
		ResultSet result = null;

		dbc = DatabaseConnect.getInstance();

		try
		{
			result = dbc.getData(sqlCmd);
			if (result != null)
			{
				result.beforeFirst();
				while (result.next())
				{
					constructTale(result);
				}
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * All Tales get to be constructed with their own data
	 * 
	 * @param result
	 * @throws SQLException
	 */
	private static void constructTale(ResultSet result) throws SQLException
	{
		String taleName = "";
		String tellerID = "";
		String roleID = "";
		String mainChannelID = "";
		String secondChannelID = "";
		ThdlMember storyteller = null;
		Role taleRole = null;
		TextChannel mainChannel = null;
		VoiceChannel secondaryChannel = null;

		taleName = result.getString(2);
		tellerID = result.getString(3);
		roleID = result.getString(4);
		mainChannelID = result.getString(5);
		secondChannelID = result.getString(6);

		storyteller = ThdlMemberFactory.getMemberByID(tellerID);
		taleRole = RoleFactory.getRoleByID(roleID);
		mainChannel = TextChannelFactory.getChannel(mainChannelID);
		secondaryChannel = VoiceChannelFactory.getChannel(secondChannelID);

		TaleFactory.createTale(taleName, storyteller, taleRole, mainChannel, secondaryChannel);
	}
}
