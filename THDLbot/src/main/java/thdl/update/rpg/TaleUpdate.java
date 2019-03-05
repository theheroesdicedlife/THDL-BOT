package thdl.update.rpg;


import java.sql.SQLException;
import thdl.bot.DatabaseConnect;
import thdl.lib.rpg.Tale;


public class TaleUpdate
{

	private static TaleUpdate instance = null;

	private DatabaseConnect dbc = null;

	private TaleUpdate()
	{
		dbc = DatabaseConnect.getInstance();
	}

	/**
	 * Inserts a new tale into the database after the command for creating a new
	 * tale was used
	 * 
	 * @param t
	 *            is the new Tale
	 * @throws SQLException
	 * @TODO: Add SQL
	 */
	public void addNewTale(Tale t) throws SQLException
	{
		String sql = "";
		dbc.updateData(sql);
	}

	public static TaleUpdate getInstance()
	{
		if (instance == null)
		{
			instance = new TaleUpdate();
		}
		return instance;
	}
}
