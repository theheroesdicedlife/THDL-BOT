package thdl.factories.discord;


import java.util.ArrayList;
import java.util.HashMap;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.GuildController;
import thdl.bot.ILogMain;
import thdl.bot.IMainUtil;
import thdl.log.LogMessageType;
import thdl.log.Logger;
import thdl.log.LoggerManager;


public class VoiceChannelFactory
{

	private static HashMap<String, VoiceChannel> channelC = new HashMap<String, VoiceChannel>();

	/**
	 * Creates a list of the VoiceChannels in the connected Guild
	 * 
	 * @param host
	 */
	public static void createVoiceChannelMap(Guild host)
	{
		if (channelC.isEmpty())
		{
			for (VoiceChannel vc : host.getVoiceChannels())
			{
				channelC.put(vc.getId(), vc);
			}
		}
	}

	/**
	 * Returns a VoiceChannel for the id or null if the id is not found
	 * 
	 * @param channelID
	 * @return
	 */
	public static VoiceChannel getChannel(String channelID)
	{
		VoiceChannel voice = null;
		if (channelC.containsKey(channelID))
		{
			voice = channelC.get(channelID);
		}
		return voice;
	}

	/**
	 * Returns a TextChannel, which is created by a GuildController-Object.
	 * 
	 * @param controller
	 *            GuildController of the calling method
	 * @param channelName
	 *            Name for the new VoiceChannel
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
	public static VoiceChannel createVoiceChannel(GuildController controller, String channelName, Category category,
			Role targetOne, Role targetTwo, ArrayList<Permission> allowOne, ArrayList<Permission> allowTwo,
			ArrayList<Permission> denyOne, ArrayList<Permission> denyTwo) throws Exception
	{
		Logger log = LoggerManager.getLogger(ILogMain.NUM, ILogMain.NAME);
		VoiceChannel voice = null;
		try
		{
			voice = controller.createVoiceChannel(channelName).setParent(category)
					.addPermissionOverride(targetOne, allowOne, denyOne)
					.addPermissionOverride(targetTwo, allowTwo, denyTwo).complete();
		}
		catch (Exception e)
		{
			log.addMessageToLog(ILogMain.TXT_CH_F, LogMessageType.EXCEPTION, ILogMain.NO_ADMIN_PERM,
					IMainUtil.NO_ADMIN_PERMISSION_BOT);
			throw e;
		}
		return voice;
	}
}
