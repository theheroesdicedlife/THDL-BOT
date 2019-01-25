package thdl.factories;

import java.sql.SQLException;
import thdl.rpg.lib.Player;
import thdl.rpg.lib.RoundTurn;
import thdl.rpg.lib.Tale;

public class TurnFactory
{
	public RoundTurn getTurn(Player player, Tale tale) throws SQLException
	{
		RoundTurn turn = new RoundTurn(player, tale);

		turn.fillMana();
		turn.fillStamina();
		turn.safeNew();

		return turn;
	}
}
