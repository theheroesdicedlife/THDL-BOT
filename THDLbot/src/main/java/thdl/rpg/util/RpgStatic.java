package thdl.rpg.util;

public interface RpgStatic
{
	// ages
	public static final String[]	AGES				= { "young", "midaged", "old" };
	public static final int			YOUNG				= 0;
	public static final int			MIDAGED				= 1;
	public static final int			OLD					= 2;
	public static final int			OUT_OF_AGE_RANGE	= 3;

	// political Standing
	public static String[]			POLITICS			= { "bondsman", "beggar", "worker", "citizen", "aristocrate",
	        "baron", "lord", "prince", "king", "emperor" };
	public static final int			BONDSMAN			= 0;
	public static final int			BEGGAR				= 1;
	public static final int			WORKER				= 2;
	public static final int			CITIZEN				= 3;
	public static final int			ARISTOCRATE			= 4;
	public static final int			BARON				= 5;
	public static final int			LORD				= 6;
	public static final int			PRINCE				= 7;
	public static final int			KING				= 8;
	public static final int			EMPEROR				= 9;
	public static final int			RANDOM_STAND		= 10;

}
