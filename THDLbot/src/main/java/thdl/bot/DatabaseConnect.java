package thdl.bot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import thdl.util.Secrets;
import thdl.util.Static;

public class DatabaseConnect
{
	private static DatabaseConnect	instance	= null;
	private Connection				aConnection	= null;

	private DatabaseConnect()
	{
		openDB();
	}

	private void openDB()
	{
		String connectionString = "jdbc:mysql://" + Static.DATABASE_HOST_NAME + ":" + Static.DATABASE_PORT + "/"
		        + Static.DATABASE_PATH;
		try
		{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			aConnection = DriverManager.getConnection(connectionString, Static.DATABASE_LOGIN,
			        Secrets.databasePassword);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ResultSet getData(String sql) throws SQLException
	{
		ResultSet rs = null;

		reconnect();

		Statement stm = aConnection.createStatement();
		rs = stm.executeQuery(sql);

		return rs;
	}

	public int updateData(String sql) throws SQLException
	{
		reconnect();
		int row = -1;

		Statement stm = aConnection.createStatement();
		row = stm.executeUpdate(sql);

		return row;
	}

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static DatabaseConnect getInstance()
	{
		if (instance == null)
		{
			instance = new DatabaseConnect();
		}

		return instance;
	}
}
