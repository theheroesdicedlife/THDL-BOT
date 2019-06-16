package thdl.lib.rpg;


import thdl.lib.discord.ThdlMember;


public class Turn
{

	private Tale		tale	= null;
	private ThdlMember	member	= null;

	// CONSTRUCTOR

	public Turn(Tale tale, ThdlMember member)
	{
		this.tale = tale;
		this.member = member;
	}

	// GETTER

	/**
	 * 
	 * @return
	 * 		the tale of the turn
	 */
	public Tale getTale()
	{
		return tale;
	}

	/**
	 * 
	 * @return
	 * 		the player of the turn
	 */
	public ThdlMember getMember()
	{
		return member;
	}

	// SETTER

	/**
	 * 
	 * @param tale
	 */
	private void setTale(Tale tale)
	{
		this.tale = tale;
	}

	/**
	 * 
	 * @param member
	 */
	private void setMember(ThdlMember member)
	{
		this.member = member;
	}

}
