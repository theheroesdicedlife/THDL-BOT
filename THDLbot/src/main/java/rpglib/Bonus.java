package rpglib;

public class Bonus
{
	private String	cause		= "";
	private int		stat		= 0;
	private int		value		= 0;
	private int		duration	= 0;

	public String getCause()
	{
		return cause;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public int getStat()
	{
		return stat;
	}

	public int getValue()
	{
		return value;
	}

	public Bonus(int value, int stat, String cause, int time)
	{
		this.value = value;
		this.stat = stat;
		this.cause = cause;
		this.duration = time;
	}

	public int lowerDuration()
	{
		return duration--;
	}

}
