package thdl.bot;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import thdl.util.ISecrets;
import thdl.util.IStatic;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class DatabaseConnect
{

	private static DatabaseConnect	instance	= null;
	private Connection				aConnection	= null;
	private Logger					log			= null;

	private DatabaseConnect()
	{
		log = LoggerManager.getLogger(ILogMain.NUM, ILogMain.NAME);
		openDB();
	}

	/**
	 * Creates a connection to the Database (MySQL)
	 */
	private void openDB()
	{
		String connectionString = IStatic.DATABASE_NAME + IStatic.DATABASE_HOST_NAME + ":" + IStatic.DATABASE_PORT + "/"
				+ IStatic.DATABASE_PATH;
		try
		{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			aConnection = DriverManager.getConnection(connectionString, IStatic.DATABASE_LOGIN,
					ISecrets.databasePassword);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			log.logException(this.toString(), ILogMain.DB_CONNECT, e.getMessage());
		}
	}

	/**
	 * Makes a new connection, if the connection was lost
	 */
	public void reconnect()
	{
		try
		{
			if (aConnection.isClosed())
			{
				openDB();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			log.logException(this.toString(), ILogMain.DB_RECONNECT, e.getMessage());
		}
	}

	/**
	 * Reads saved data from the database
	 * 
	 * @param sql
	 *            sql-command used for the query
	 * @return
	 * 		the result is returned in a ResultSet
	 * @throws SQLException
	 */
	public ResultSet getData(String sql) throws SQLException
	{
		ResultSet rs = null;
		reconnect();
		Statement stm = aConnection.createStatement();
		rs = stm.executeQuery(sql);
		return rs;
	}

	/**
	 * Updates a database-table with change data
	 * 
	 * @param sql
	 *            command used for the query
	 * @return
	 * 		returns the updated row
	 * @throws SQLException
	 */
	public int updateData(String sql) throws SQLException
	{
		reconnect();
		int row = -1;
		Statement stm = aConnection.createStatement();
		row = stm.executeUpdate(sql);
		return row;
	}

	/**
	 * Closes the connection
	 */
	public void closeDB()
	{
		if (aConnection != null)
		{
			try
			{
				if (!aConnection.isClosed())
				{
					aConnection.close();
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				log.logException(this.toString(), ILogMain.DB_CONNECT_CLOSE, e.getMessage());
			}
		}
	}

	/**
	 * Creates an instance-object of the DatabaseConnect-Class
	 * 
	 * @return
	 */
	public static DatabaseConnect getInstance()
	{
		if (instance == null)
		{
			instance = new DatabaseConnect();
		}
		return instance;
	}
}
