package rpglib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class Tale implements Stats
{
	private String					name;
	private Member					storyteller;
	private Role					myRole;
	private TextChannel				myText;
	private VoiceChannel			myVoice;
	private HashMap<Member, Player>	playerOfThisTale		= null;
	private Boolean					isStarted				= false;
	private HashMap<String, Player>	playerOverCharacterName	= null;
	private ArrayList<Turn>			playerTurnList			= null;
	private int						turnCounter;
	private int						maxTurnsPerRound;
	private int						roundCounter;

	public Tale(String name, Member storyteller)
	{
		this.name = name;
		this.storyteller = storyteller;
		playerOfThisTale = new HashMap<>();
		playerOverCharacterName = new HashMap<>();
		playerTurnList = new ArrayList<>();
		turnCounter = -1;
		maxTurnsPerRound = 0;
		roundCounter = 0;
	}

	public void setBasics(Role role, TextChannel txtchan, VoiceChannel vcchan)
	{
		myRole = role;
		myText = txtchan;
		myVoice = vcchan;
	}

	public int getNumberOfPlayers()
	{
		return playerOfThisTale.size();
	}

	public boolean isPlayerInTale(Member m)
	{
		return playerOfThisTale.containsKey(m);
	}

	public void addPlayer(Player p)
	{
		playerOfThisTale.put(p.getMySelf(), p);
	}

	public void addCharacternameToPlayer(Player p)
	{
		playerOverCharacterName.put(p.getMyCharacter().getName(), p);
	}

	public Player getPlayer(Member m)
	{
		return playerOfThisTale.get(m);
	}

	public Player getPlayer(String charactername)
	{
		return playerOverCharacterName.get(charactername);
	}

	public boolean isNameInUse(String name)
	{
		return playerOverCharacterName.containsKey(name);
	}

	public void getOrder()
	{
		playerOfThisTale.forEach((member, player) ->
			{
				createTurn(player);
			});
		Collections.sort(playerTurnList, (turn1, turn2) -> Integer.valueOf(turn1.getMyCharacter().getStats()[MOV][0])
		        .compareTo(Integer.valueOf(turn2.getMyCharacter().getStats()[MOV][0])));
		maxTurnsPerRound = playerTurnList.size();
		nextTurn();
	}

	private void createTurn(Player p)
	{
		if (p.getMyTurn() == null)
		{
			Turn aTurn = new Turn(p);
			p.addTurn(aTurn);
			playerTurnList.add(aTurn);
		}
	}

	private void nextTurn()
	{
		turnCounter++;
		if (turnCounter < maxTurnsPerRound)
		{
			turnCounter = 0;
			roundCounter++;
		}
	}

	public Turn getCurrentTurn()
	{
		return playerTurnList.get(turnCounter);
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
}
