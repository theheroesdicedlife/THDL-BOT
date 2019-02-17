package thdl.commands.guildMessage;


import java.util.Random;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;


public interface IDiced
{

	/**
	 * Creates one random number
	 * 
	 * @param rand
	 * @return
	 */
	public abstract int diceOnce(Random rand);

	/**
	 * Creates multiple random numbers and adds the up.
	 * 
	 * @param rand
	 * @param quantity
	 *            The quantity of random numbers created
	 * @return
	 */
	public abstract int diceMultiple(Random rand, int quantity);

	/**
	 * Secures the results of the dice-command in the turn-object of the player
	 * 
	 * @param result
	 * @param e
	 */
	public abstract void secureDiceResult(int result, GuildMessageReceivedEvent e);
}
