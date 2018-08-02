package commands;

import java.util.Random;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public interface IDiced
{
	public abstract int diceOnce(Random rand);

	public abstract int diceMultiple(Random rand, int quantity);

	public abstract void secureDiceResult(int result, GuildMessageReceivedEvent e);
}
