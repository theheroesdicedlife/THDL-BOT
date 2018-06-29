package rpglib;

import net.dv8tion.jda.core.entities.Member;

public class Player
{
	private Member			mySelf		= null;
	private Tale			myTale		= null;
	private PlayerCharacter	myCharacter	= null;

	public Player(Member member, Tale tale)
	{
		mySelf = member;
		myTale = tale;
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

}
