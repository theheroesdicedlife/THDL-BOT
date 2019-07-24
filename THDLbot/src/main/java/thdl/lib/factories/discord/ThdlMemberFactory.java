package thdl.lib.factories.discord;


import java.util.HashMap;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import thdl.bot.ILogMain;
import thdl.bot.IMainUtil;
import thdl.lib.discord.ThdlMember;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class ThdlMemberFactory
{

	private static ThdlMemberFactory instance = null;

	/**
	 * Key = Id of a Discord-User
	 * Value = specific ThdlMember for that same user
	 */
	private HashMap<String, ThdlMember> memberList = null;

	private ThdlMemberFactory()
	{
		memberList = new HashMap<String, ThdlMember>();
	}

	public static ThdlMemberFactory getInstance()
	{
		if (instance == null)
		{
			instance = new ThdlMemberFactory();
		}
		return instance;
	}

	/**
	 * Takes the members in the connected guild and creates a ThdlMember-Object for
	 * each of them, if the member is not in the list yet.
	 * 
	 * 
	 * @param host
	 */
	public void createMapOfMembers(Guild host)
	{
		Logger log = LoggerManager.getLogger(ILogMain.NUM, ILogMain.NAME);
		if (host != null)
		{
			for (Member m : host.getMembers())
			{
				String memberID = m.getUser().getId();
				if (!memberList.containsKey(memberID))
				{
					ThdlMember thdlm = new ThdlMember(m);
					thdlm.setRoles();

					memberList.put(memberID, thdlm);
				}
			}
			log.logState(ILogMain.THDL_MEMBER_F, IMainUtil.GUILD_MAPPING_FIN);
		}
		else
		{
			log.logErrorWithoutMsg(ILogMain.THDL_MEMBER_F, IMainUtil.NO_GUILD);
		}
	}

	/**
	 * Returns the ThdlMember for the specific user
	 * 
	 * @param user
	 * @return
	 * 		An member or null if nothing was found
	 */
	public ThdlMember getMember(User user)
	{
		if (user != null)
		{
			String memberID = user.getId();
			return memberList.get(memberID);
		}
		else
		{
			return null;
		}
	}

	/**
	 * Returns the ThdlMember for the specific user-id
	 * 
	 * @param id
	 * @return
	 * 		An member or null if nothing was found
	 */
	public ThdlMember getMember(String id)
	{
		if (id != "")
			return memberList.get(id);
		else
			return null;
	}

}
