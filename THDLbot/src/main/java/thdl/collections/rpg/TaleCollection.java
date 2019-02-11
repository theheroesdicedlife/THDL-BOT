package thdl.collections.rpg;


import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import thdl.collections.Collection;
import thdl.rpg.lib.Tale;


public class TaleCollection
{

	/**
	 * Key1 = talename
	 * Key2 = channelid
	 * Key3 = userid of storyteller
	 */
	private Collection<String, String, String, Tale> taleC = null;

	public TaleCollection()
	{
		taleC = new Collection<String, String, String, Tale>();
	}

	/**
	 * Adds a tale-object to the collection
	 * The first key is the name of the tale
	 * The second key is the Discord-specific id of the textchannel existend for the
	 * tale
	 * The third key is the Discord-specific id of the user who is the storyteller
	 * of this tale
	 * 
	 * @param tale
	 * @return
	 */
	public boolean addTale(Tale tale)
	{
		String channelID = tale.getMainChannel().getId();
		String memberID = tale.getStoryteller().getMember().getUser().getId();

		return (taleC.add(tale, tale.getTaleName(), channelID, memberID));
	}

	public Tale getTaleByName(String taleName)
	{
		return taleC.getByFirstKey(taleName);
	}

	public Tale getTaleByChannel(TextChannel mainChannel)
	{
		String channelID = mainChannel.getId();
		return taleC.getBySecondKey(channelID);
	}

	public Tale getTaleByTeller(Member storyteller)
	{
		String memberID = storyteller.getUser().getId();
		return taleC.getByThirdKey(memberID);
	}

	public boolean containsChannel(TextChannel mainChannel)
	{
		String channelID = mainChannel.getId();
		return taleC.containsSecondKey(channelID);
	}

	public boolean containsName(String taleName)
	{
		return taleC.containsFirstKey(taleName);
	}
}
