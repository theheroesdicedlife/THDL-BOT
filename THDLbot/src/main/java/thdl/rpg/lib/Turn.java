package thdl.rpg.lib;

import java.sql.SQLException;

public interface Turn
{
	public abstract void safe() throws SQLException;

	public abstract void safeNew() throws SQLException;

	public abstract int getInit();

}
