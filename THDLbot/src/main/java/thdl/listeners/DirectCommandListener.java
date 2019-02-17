package thdl.listeners;


import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import thdl.bot.ILogMain;
import thdl.discord.ThdlMember;
import thdl.factories.discord.ThdlMemberFactory;
import thdl.log.LogMessageType;
import thdl.log.Logger;
import thdl.log.LoggerManager;
import thdl.util.directMessage.DirectMessageHandler;
import thdl.util.directMessage.DirectMessageParser;


public class DirectCommandListener extends ListenerAdapter
{

	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event)
	{
		String raw = event.getMessage().getContentDisplay();
		ThdlMember author = ThdlMemberFactory.getMember(event.getAuthor());
		String selfID = event.getJDA().getSelfUser().getId();
		String authorID = event.getAuthor().getId();
		Logger log = LoggerManager.getLogger(ILogMain.NUM, ILogMain.NAME);

		if (!authorID.equals(selfID) && author.isAdmin())
		{
			log.logState(this.toString(), event.getMessage().toString());
			try
			{
				DirectMessageHandler.handleCommand(new DirectMessageParser(raw, event));
			}
			catch (Exception e)
			{
				log.addMessageToLog(this.toString(), LogMessageType.EXCEPTION, ILogMain.CMD_HANDLE_EXC, e.getMessage());
			}
		}
	}
}
