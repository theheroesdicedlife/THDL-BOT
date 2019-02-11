package thdl.listeners;


import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import thdl.bot.CommandHandler;
import thdl.bot.ILogMain;
import thdl.discord.ThdlMember;
import thdl.factories.discord.ThdlMemberFactory;
import thdl.log.LogMessageType;
import thdl.log.Logger;
import thdl.log.LoggerManager;
import thdl.util.DiscordID;
import thdl.util.Static;


public class CommandListener extends ListenerAdapter
{

	/**
	 * Occurs, if a command is written in a Textchannel of the connected guild
	 * The Command is only accepted if certain conditions are fulfilled
	 */
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String raw = "";
		Logger log = LoggerManager.getLogger(ILogMain.NUM, ILogMain.NAME);
		Message msg = event.getMessage();
		TextChannel channel = msg.getTextChannel();
		ThdlMember member = ThdlMemberFactory.getMember(event.getAuthor());
		// does the Message has the right beginning char
		// the sender is not the bot itself
		// the sender does not have the role guest
		// the message was send in Botting/ rpgBot_Listens_here/Cat for rpg Textchannel
		if (msg.getContentDisplay().startsWith(Static.PREFIX)
				&& msg.getAuthor().getId() != event.getJDA().getSelfUser().getId() && !member.isGuest()
				&& (channel.getId().equals(DiscordID.BOTTING_ID)
						|| channel.getId().equals(DiscordID.BOTISLISTENINGHERE_ID)
						|| channel.getParent().getId().equals(DiscordID.RPGTXTCAT_ID)))
		{
			raw = msg.getContentDisplay();
			log.logState(this.toString(), event.getMessage().toString());
			try
			{
				CommandHandler.handleCommand(CommandHandler.parse.parser(raw, event));
			}
			catch (Exception e)
			{
				log.addMessageToLog(this.toString(), LogMessageType.EXCEPTION, ILogMain.CMD_HANDLE_EXC, e.getMessage());
			}
		}
	}
}
