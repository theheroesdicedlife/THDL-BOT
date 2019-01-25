package thdl.rpg.lib;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import thdl.bot.DatabaseConnect;
import thdl.factories.TurnFactory;
import thdl.rpg.util.RpgStatic;

public class Tale implements RpgStatic
{
	private String					name;
	private Member					storyteller;
	private Role					myRole;
	private TextChannel				myText;
	private VoiceChannel			myVoice;
	private HashMap<Member, Player>	playerOfThisTale		= null;
	private Boolean					isStarted				= false;
	private HashMap<String, Player>	playerOverCharacterName	= null;
	private int						currentTurn				= 0;
	private int						maxTurn					= 0;
	private int						roundCounter			= 0;
	private boolean					areJobsEnabled			= true;
	private ArrayList<String>		playableRaces			= null;
	private int						settingPoliticalStand	= 0;
	private ArrayList<RoundTurn>	turnsOfTale				= null;

	public Tale(String name, Member storyteller)
	{
		this.name = name;
		this.storyteller = storyteller;
		playerOfThisTale = new HashMap<>();
		playerOverCharacterName = new HashMap<>();
		playableRaces = new ArrayList<>();
		setPlayableRaces(null);
		turnsOfTale = new ArrayList<>();
	}

	public void setBasics(Role role, TextChannel txtchan, VoiceChannel vcchan)
	{
		myRole = role;
		myText = txtchan;
		myVoice = vcchan;
	}

	public void setNumbers(int currentTurn, int maxTurn, int roundCounter)
	{
		this.currentTurn = currentTurn;
		this.maxTurn = maxTurn;
		this.roundCounter = roundCounter;
	}

	public void setPlayableRaces(ArrayList<String> races)
	{
		if (races == null)
		{
			for (String s : Races.getStandardRaces())
			{
				playableRaces.add(s);
			}
		}
		else
		{
			playableRaces.addAll(races);
		}
	}

	public boolean isPlayerInTale(Member m)
	{
		return playerOfThisTale.containsKey(m);
	}

	public void addPlayer(ArrayList<Player> players) throws SQLException
	{
		for (Player p : players)
		{
			playerOfThisTale.put(p.getMySelf(), p);
		}

		safePlayerNumber();
	}

	public void addCharacter(String characterName, Player player)
	{
		playerOverCharacterName.put(characterName, player);
	}

	public Player getPlayer(Member m)
	{
		return playerOfThisTale.get(m);
	}

	public boolean isCharacterNameInUse(String name)
	{
		return playerOverCharacterName.containsKey(name);
	}

	public boolean isThisMyStoryTeller(User author)
	{
		String authorID = author.getId();
		String storytellerID = storyteller.getUser().getId();

		return authorID.equals(storytellerID);
	}

	public int getPlayerNumber()
	{
		return playerOfThisTale.size();
	}

	public void orderTurns() throws SQLException
	{
		Collection<Player> players = playerOfThisTale.values();
		for (Player p : players)
		{
			RoundTurn turn = p.getMyTurn();

			if (turn != null)
			{
				turnsOfTale.add(turn);
			}
			else
			{
				if (p.getMyCharacter() != null)
				{
					turnsOfTale.add(new TurnFactory().getTurn(p, this));
				}
			}
		}

		maxTurn = turnsOfTale.size();

		turnsOfTale
		        .sort((turn1, turn2) -> Integer.valueOf(turn1.getInit()).compareTo(Integer.valueOf(turn2.getInit())));

	}

	public RoundTurn getCurrentTurn()
	{

		for (int i = 0; i < turnsOfTale.size(); i++)
		{
			RoundTurn t = turnsOfTale.get(i);
			if (t.isAct())
			{
				currentTurn = i;
				break;
			}
		}

		return turnsOfTale.get(currentTurn);
	}

	public void incrementTurnCounter()
	{
		currentTurn++;
		if (currentTurn > maxTurn)
		{
			currentTurn = 1;
			roundCounter++;
		}
	}

	public void safeNew() throws SQLException
	{

		DatabaseConnect dbc = DatabaseConnect.getInstance();
		String stID = storyteller.getUser().getId();
		String vcID = myVoice.getId();
		String txtID = myText.getId();
		String roleID = myRole.getId();

		String sql = "";
		sql = sql + "INSERT INTO Tale (Tale_ID, Tale_Name, Storyteller, Voicechannel, Textchannel, Role) ";
		sql = sql + "VALUES (UUID(), '" + name + "', '" + stID + "', '" + vcID + "', '" + txtID + "', '" + roleID
		        + "')";

		dbc.updateData(sql);
	}

	public void safePlayerNumber() throws SQLException
	{
		DatabaseConnect dbc = DatabaseConnect.getInstance();
		String sql = "";
		sql = sql + "UPDATE Tale SET Player_Number = " + playerOfThisTale.size() + " WHERE tale_Name = '" + name + "'";

		dbc.updateData(sql);
	}

	public void safe() throws SQLException
	{
		DatabaseConnect dbc = DatabaseConnect.getInstance();
		String sql = "";
		sql = sql + "UPDATE Tale SET ";
		sql = sql + "Player_Number =" + playerOfThisTale.size() + ", CurrentTurn = " + currentTurn + ", ";
		sql = sql + "MaxTurn = " + maxTurn + ", RoundCount = " + roundCounter;

		dbc.updateData(sql);
	}

	public boolean getPlayableRace(String raceName)
	{
		return playableRaces.contains(raceName);
	}

	public String getName()
	{
		return name;
	}

	private void setName(String name)
	{
		this.name = name;
	}

	public Member getStoryteller()
	{
		return storyteller;
	}

	public void setStoryteller(Member storyteller)
	{
		this.storyteller = storyteller;
	}

	public Role getMyRole()
	{
		return myRole;
	}

	private void setMyRole(Role myRole)
	{
		this.myRole = myRole;
	}

	public TextChannel getMyText()
	{
		return myText;
	}

	private void setMyText(TextChannel myText)
	{
		this.myText = myText;
	}

	public VoiceChannel getMyVoice()
	{
		return myVoice;
	}

	private void setMyVoice(VoiceChannel myVoice)
	{
		this.myVoice = myVoice;
	}

	public Boolean getIsStarted()
	{
		return isStarted;
	}

	public void setIsStarted(Boolean isStarted)
	{
		this.isStarted = isStarted;
	}

	public boolean isAreJobsEnabled()
	{
		return areJobsEnabled;
	}

	public void setAreJobsEnabled(boolean areJobsEnabled)
	{
		this.areJobsEnabled = areJobsEnabled;
	}

	public int getSettingPoliticalStand()
	{
		return settingPoliticalStand;
	}
}
