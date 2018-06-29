package rpglib;

import java.util.HashMap;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class Tale
{
	private String					name;
	private Member					storyteller;
	private Role					myRole;
	private TextChannel				myText;
	private VoiceChannel			myVoice;
	private HashMap<Member, Player>	playerOfThisTale		= null;
	private Boolean					isStarted				= false;
	private HashMap<String, Player>	playerOverCharacterName	= null;

	public Tale(String name, Member storyteller)
	{
		this.name = name;
		this.storyteller = storyteller;
		playerOfThisTale = new HashMap<>();
		playerOverCharacterName = new HashMap<>();
	}

	public void setBasics(Role role, TextChannel txtchan, VoiceChannel vcchan)
	{
		myRole = role;
		myText = txtchan;
		myVoice = vcchan;
	}

	public boolean isPlayerInTale(Member m)
	{
		return playerOfThisTale.containsKey(m);
	}

	public void addPlayer(Player p)
	{
		playerOfThisTale.put(p.getMySelf(), p);
	}

	public Player getPlayer(Member m)
	{
		return playerOfThisTale.get(m);
	}

	public boolean isNameInUse(String name)
	{
		return playerOverCharacterName.containsKey(name);
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
