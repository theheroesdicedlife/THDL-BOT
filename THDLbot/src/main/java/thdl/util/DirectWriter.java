package thdl.util;


import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import thdl.lib.discord.ThdlMember;


public class DirectWriter
{

	private PrivateChannel channel = null;

	public DirectWriter(User user) throws Exception
	{
		this.channel = user.openPrivateChannel().complete();
	}

	public DirectWriter(PrivateChannel channel)
	{
		this.channel = channel;
	}

	public DirectWriter(Member member) throws Exception
	{
		this.channel = member.getUser().openPrivateChannel().complete();
	}

	public DirectWriter(ThdlMember member) throws Exception
	{
		this.channel = member.getMember().getUser().openPrivateChannel().complete();
	}

	public void writeMsg(String msg)
	{
		channel.sendMessage(msg);
	}

}
