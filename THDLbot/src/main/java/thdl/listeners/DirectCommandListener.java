package thdl.listeners;


import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import thdl.bot.ILogMain;
import thdl.lib.discord.ThdlMember;
import thdl.lib.factories.discord.ThdlMemberFactory;
import thdl.util.BlackList;
import thdl.util.directMessage.DirectMessageHandler;
import thdl.util.directMessage.DirectMessageParser;
import thdl.util.log.LogMessageType;
import thdl.util.log.Logger;
import thdl.util.log.LoggerManager;


public class DirectCommandListener extends ListenerAdapter
{

	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event)
	{
		String raw = event.getMessage().getContentDisplay();
		ThdlMember author = ThdlMemberFactory.getInstance().getMember(event.getAuthor());
		String selfID = event.getJDA().getSelfUser().getId();
		String authorID = event.getAuthor().getId();
		Logger log = LoggerManager.getLogger(ILogMain.NUM, ILogMain.NAME);

		if (!authorID.equals(selfID) && author != null)
		{
			if (author.isAllowed() && !BlackList.isBlocked(event.getAuthor()))
			{
				log.logState(this.toString(), event.getMessage().toString());
				try
				{
					DirectMessageHandler.getInstance().handleCommand(new DirectMessageParser(raw, event));
				}
				catch (Exception e)
				{
					log.addMessageToLog(this.toString(), LogMessageType.EXCEPTION, ILogMain.CMD_HANDLE_EXC,
							e.getMessage());
				}
			}
		}
	}
}
