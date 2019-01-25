package thdl.rpg.lib;

import java.sql.SQLException;
import thdl.bot.DatabaseConnect;
import thdl.rpg.util.Stats;

public class RoundTurn implements Turn, Stats
{
	private Player	owner	= null;
	private Tale	tale	= null;
	private int		stamina	= 0;
	private int		mana	= 0;
	private boolean	isAct	= false;
	private int		hunger	= 100;
	private int		thirst	= 100;

	public RoundTurn(Player owner, Tale tale)
	{
		super();
		this.owner = owner;
		this.tale = tale;
		owner.setMyTurn(this);
	}

	public void fillStamina()
	{
		stamina = owner.getMyCharacter().getAttribute()[VIT][0] * 5;
	}

	public void fillMana()
	{
		mana = owner.getMyCharacter().getAttribute()[PSY][0] * 5;
	}

	public void safeNew() throws SQLException
	{
		DatabaseConnect dbc = DatabaseConnect.getInstance();
		String memberID = owner.getMySelf().getUser().getId();

		String sql = "";
		sql = sql + "INSERT INTO Roundturn (Roundturn_ID, Player, Tale, Stamina, Mana, Hunger, Thirst) ";
		sql = sql + "VALUES (UUID(), ";
		sql = sql + "(SELECT player_ID FROM Player WHERE User = '" + memberID + "'), ";
		sql = sql + "(SELECT tale_ID FROM Tale WHERE Tale_Name = '" + tale.getName() + "'), ";
		sql = sql + stamina + ", " + mana + ", " + hunger + ", " + thirst + ")";

		dbc.updateData(sql);

	}

	public void safe() throws SQLException
	{
		DatabaseConnect dbc = DatabaseConnect.getInstance();

		String sql = "";
		sql = sql + "UPDATE Roundturn ";
		sql = sql + "SET Stamina = " + stamina + ", mana = " + mana + ", ";
		sql = sql + "Hunger = " + hunger + ", Thirst = " + thirst + ", isCurrent = " + isAct;

		dbc.updateData(sql);
	}

	public int getInit()
	{
		return owner.getMyCharacter().getAttribute()[MOV][0];
	}

	public boolean isAct()
	{
		return isAct;
	}

	public void setAct(boolean isAct)
	{
		this.isAct = isAct;
	}

	public Player getOwner()
	{
		return owner;
	}

	public int getHunger()
	{
		return hunger;
	}

	public void lowerHunger(int hunger)
	{
		this.hunger = this.hunger - hunger;
	}

	public int getThirst()
	{
		return thirst;
	}

	public void lowerThirst(int thirst)
	{
		this.thirst = this.thirst - thirst;
	}

	public int getStamina()
	{
		return stamina;
	}

	public void setStamina(int stamina)
	{
		this.stamina = stamina;
	}

	public int getMana()
	{
		return mana;
	}

	public void setMana(int mana)
	{
		this.mana = mana;
	}

	public void setHunger(int hunger)
	{
		this.hunger = hunger;
	}

	public void setThirst(int thirst)
	{
		this.thirst = thirst;
	}

}
