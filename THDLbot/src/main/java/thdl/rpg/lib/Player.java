package thdl.rpg.lib;

import java.sql.SQLException;
import net.dv8tion.jda.core.entities.Member;
import thdl.bot.DatabaseConnect;

public class Player
{
	private Member			mySelf		= null;
	private Tale			myTale		= null;
	private PlayerCharacter	myCharacter	= null;
	private RoundTurn		myTurn		= null;

	public Player(Member member, Tale tale)
	{
		mySelf = member;
		myTale = tale;
	}

	public void safeNewPlayer() throws SQLException
	{
		DatabaseConnect dbc = DatabaseConnect.getInstance();

		String userID = mySelf.getUser().getId();

		String sql = "";
		sql = sql + "INSERT INTO Player ";
		sql = sql + "(Player_ID, User, Tale) VALUES ";
		sql = sql + "(UUID(), '" + userID + "', ";
		sql = sql + "(SELECT Tale_ID FROM Tale WHERE Tale_Name = '" + myTale.getName() + "'))";

		dbc.updateData(sql);
	}

	public void safePlayer() throws SQLException
	{
		DatabaseConnect dbc = DatabaseConnect.getInstance();

		String userID = mySelf.getUser().getId();
		String charaName = myCharacter.getName();

		String sql = "";

		if (myTurn == null)
		{
			sql = sql + "UPDATE Player SET Player_Character = ";
			sql = sql + "(SELECT TCharacter_ID FROM Tale_Character WHERE Character_Name = '" + charaName + "') ";
			sql = sql + "WHERE user = '" + userID + "'";
		}
		else
		{

		}

		dbc.updateData(sql);
	}

	public Member getMySelf()
	{
		return mySelf;
	}

	public Tale getMyTale()
	{
		return myTale;
	}

	public void setMyCharacter(PlayerCharacter myCharacter)
	{
		this.myCharacter = myCharacter;
	}

	public PlayerCharacter getMyCharacter()
	{
		return myCharacter;
	}

	public RoundTurn getMyTurn()
	{
		return myTurn;
	}

	public void setMyTurn(RoundTurn myTurn)
	{
		this.myTurn = myTurn;
	}

}
