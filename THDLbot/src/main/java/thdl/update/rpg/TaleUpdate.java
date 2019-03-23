package thdl.update.rpg;


import java.sql.SQLException;
import thdl.bot.DatabaseConnect;
import thdl.lib.rpg.Tale;


public class TaleUpdate implements IStaticSQL
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
	 *             TODO: Test SQL
	 */
	public void addNewTale(Tale t) throws SQLException
	{
		StringBuilder sqlBuilder = new StringBuilder();

		sqlBuilder.append(INSERT);
		sqlBuilder.append(TALE);
		sqlBuilder.append(BRK_OPEN + NAME + COMMA + TELLER);
		sqlBuilder.append(COMMA + ROLE + COMMA + TXT_CHANNEL);
		sqlBuilder.append(COMMA + VC_CHANNEL + COMMA + START_JOB_ACT + COMMA + DET_OF_EST);
		sqlBuilder.append(COMMA + DET_OF_AFF + COMMA + IS_STARTED + BRK_CLOSE);
		sqlBuilder.append(VALUES);
		sqlBuilder.append(BRK_OPEN + t.getTaleName() + COMMA + t.getStoryteller().getUserID());
		sqlBuilder.append(COMMA + t.getTaleRole().getId());
		sqlBuilder.append(COMMA + t.getMainChannel().getId() + COMMA + t.getSecondaryChannel().getId() + BRK_CLOSE);

		// dbc.updateData(sqlBuilder.toString());
	}

	/**
	 * Updates the table with the races for the tale
	 * 
	 * @param t
	 *            is the tale in which the changes have happened
	 * @throws SQLException
	 * 
	 *             TODO: Test SQL
	 */
	public void updateTaleRaces(Tale t) throws SQLException
	{
		StringBuilder sqlBuilder = new StringBuilder();

		dbc.updateData(sqlBuilder.toString());
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
