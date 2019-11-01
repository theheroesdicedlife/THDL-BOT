package thdl.lib.rpg;


import thdl.lib.discord.ThdlMember;


public class MasterTurn
{

	private ThdlMember	master	= null;
	private Tale		tale	= null;

	public MasterTurn(ThdlMember dungeonmaster, Tale tale)
	{
		this.master = dungeonmaster;
		this.tale = tale;
	}

	/**
	 * @return the master
	 */
	public ThdlMember getMaster()
	{
		return master;
	}

	/**
	 * @return the tale
	 */
	public Tale getTale()
	{
		return tale;
	}

	/**
	 * @param master
	 *            the master to set
	 */
	protected void setMaster(ThdlMember master)
	{
		this.master = master;
	}

	/**
	 * @param tale
	 *            the tale to set
	 */
	protected void setTale(Tale tale)
	{
		this.tale = tale;
	}

}
