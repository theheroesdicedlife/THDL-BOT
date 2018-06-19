package commands;

import java.util.Random;
import net.dv8tion.jda.core.entities.TextChannel;

public interface IDiced
{
	public abstract int diceOnce(Random rand);

	public abstract int diceMultiple(Random rand, int quantity);

	public abstract void secureDiceResult(int result, TextChannel txt);
}
