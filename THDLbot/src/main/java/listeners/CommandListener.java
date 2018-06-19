package listeners;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import thdl.bot.CommandHandler;
import thdl.bot.DiscordMapper;
import util.DiscordID;
import util.Static;

public class CommandListener extends ListenerAdapter
{
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String raw = "";
		Message msg = event.getMessage();
		TextChannel channel = msg.getTextChannel();

		// does the Message has the right beginning char
		// the sender is not the bot itself
		// the sender does not have the role guest
		// the message was send in Botting/ rpgBot_Listens_here/Cat for rpg Textchannel

		if (msg.getContentDisplay().startsWith(Static.PREFIX)
		        && msg.getAuthor().getId() != event.getJDA().getSelfUser().getId()
		        && !event.getMember().getRoles().contains(DiscordMapper.RoleOverName.get("~Guest"))
		        && (channel.getId().equals(DiscordID.BOTTING_ID)
		                || channel.getId().equals(DiscordID.BOTISLISTENINGHERE_ID)
		                || channel.getParent().getId().equals(DiscordID.RPGTXTCAT_ID)))
		{
			raw = msg.getContentDisplay();
			System.out.println(event.getMessage());
			CommandHandler.handleCommand(CommandHandler.parse.parser(raw, event));
		}
	}

}
