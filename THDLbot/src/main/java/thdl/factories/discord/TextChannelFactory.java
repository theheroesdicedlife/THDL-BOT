package thdl.factories.discord;


import java.util.ArrayList;
import java.util.HashMap;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.GuildController;
import thdl.bot.ILogMain;
import thdl.bot.IMainUtil;
import thdl.log.LogMessageType;
import thdl.log.Logger;
import thdl.log.LoggerManager;


public class TextChannelFactory
{

	private static HashMap<String, TextChannel> channelC = new HashMap<String, TextChannel>();

	/**
	 * Creates a list of the textchannels in the connected Guild
	 * 
	 * @param host
	 */
	public static void createTextChannelMap(Guild host)
	{
		if (channelC.isEmpty())
		{
			for (TextChannel txt : host.getTextChannels())
			{
				channelC.put(txt.getId(), txt);
			}
		}
	}

	/**
	 * Returns a TextChannel for the id or null if the id is not found
	 * 
	 * @param channelID
	 * @return
	 */
	public static TextChannel getChannel(String channelID)
	{
		TextChannel text = null;
		if (channelC.containsKey(channelID))
		{
			text = channelC.get(channelID);
		}
		return text;
	}

	/**
	 * Returns a TextChannel, which is created by a GuildController-Object.
	 * 
	 * @param controller
	 *            GuildController of the calling method
	 * @param channelName
	 *            Name for the new TextChannel
	 * @param category
	 *            Parent under which the channel is ordered
	 * @param targetOne
	 *            The first (primary) target-role for the channels permissions
	 * @param targetTwo
	 *            The second (secondary) target-role for the channels permissions
	 * @param allowOne
	 *            What Permissions has the first-target-role
	 * @param allowTwo
	 *            What Permissions has the second-target-role
	 * @param denyOne
	 *            What permissions got denied for the first-target-role
	 * @param denyTwo
	 *            What permissions got denied for the second-target-role
	 * @return
	 * @throws Exception
	 */
	public static TextChannel createTextChannel(GuildController controller, String channelName, Category category,
			Role targetOne, Role targetTwo, ArrayList<Permission> allowOne, ArrayList<Permission> allowTwo,
			ArrayList<Permission> denyOne, ArrayList<Permission> denyTwo) throws Exception
	{
		Logger log = LoggerManager.getLogger(ILogMain.NUM, ILogMain.NAME);
		TextChannel text = null;
		try
		{
			text = controller.createTextChannel(channelName).setParent(category)
					.addPermissionOverride(targetOne, allowOne, denyOne)
					.addPermissionOverride(targetTwo, allowTwo, denyTwo).complete();
			channelC.put(text.getId(), text);
		}
		catch (Exception e)
		{
			log.addMessageToLog(ILogMain.TXT_CH_F, LogMessageType.EXCEPTION, ILogMain.NO_ADMIN_PERM,
					IMainUtil.NO_ADMIN_PERMISSION_BOT);
			throw e;
		}
		return text;
	}

}
