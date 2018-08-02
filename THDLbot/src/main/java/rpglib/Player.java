package rpglib;

import net.dv8tion.jda.core.entities.Member;

public class Player
{
	private Member			mySelf		= null;
	private Tale			myTale		= null;
	private PlayerCharacter	myCharacter	= null;
	private Turn			myTurn		= null;

	public Player(Member member, Tale tale)
	{
		mySelf = member;
		myTale = tale;
	}

	protected void addTurn(Turn aTurn)
	{
		myTurn = aTurn;
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

	public Turn getMyTurn()
	{
		return myTurn;
	}

}
