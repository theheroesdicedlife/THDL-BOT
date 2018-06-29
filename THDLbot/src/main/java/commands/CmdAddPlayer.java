package commands;

import java.util.ArrayList;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;
import rpglib.Player;
import rpglib.Tale;
import rpglib.Tales;
import thdl.bot.DiscordMapper;
import thdl.bot.DiscordWriter;
import util.DiscordID;
import util.ErrorMessages;

public class CmdAddPlayer implements Command
{

	private DiscordWriter		writer			= null;

	private ArrayList<Member>	membersToAdd	= null;

	private Tale				tale			= null;

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent e)
	{
		boolean isOk = false;
		writer = openWriter(e);
		membersToAdd = new ArrayList<>();

		if (args.length < 2)
		{
			tale = Tales.getTale(e.getChannel());
			if (tale != null)
			{
				Member storyteller = tale.getStoryteller();
				String authorId = e.getAuthor().getId();
				String storytellerId = storyteller.getUser().getId();

				if (authorId.equals(storytellerId))
				{

					for (int i = 0; i < args.length; i++)
					{
						Member m = DiscordMapper.getMember(args[i]);
						if (m != null)
						{
							membersToAdd.add(m);
						}
						else
						{
							System.out.println("Member " + args[i] + " ");
						}
					}

					for (Member m : membersToAdd)
					{
						if (tale.isPlayerInTale(m))
						{
							membersToAdd.remove(m);
							System.out.println(m.getNickname() + " removed");
							writer.writeError("Member " + m.getNickname() + " already is in the Tale");
						}
						else
						{
							canMemberPlay(m);

						}
					}
					isOk = true;
				}
				else
				{
					System.out.println(ErrorMessages.PERMISSIONERROR);
					writer.writeError("You are not the storyteller of this tale");
				}
			}
			else
			{
				System.out.println(ErrorMessages.CHANNELERROR);
			}
		}
		else
		{
			System.out.println(ErrorMessages.FORMATERROR);
			writer.writeError("You should use the layout -addPlayer [nick] ([nick] ...)");
		}

		return isOk;
	}

	private void canMemberPlay(Member m)
	{
		for (int i = 0; i < DiscordID.rolenames.length; i++)
		{
			if (!DiscordMapper.isThisA(m, DiscordID.rolenames[i]))
			{
				membersToAdd.remove(m);
				System.out.println(m.getNickname() + " removed");
				writer.writeError("Member " + m.getNickname() + " is not a Fighter");
			}
		}
	}

	@Override
	public void action(String[] args, GuildMessageReceivedEvent e)
	{
		GuildController controller = e.getGuild().getController();
		Role role = tale.getMyRole();
		membersToAdd.forEach((n1) ->
			{
				giveRole(n1, controller, role);

				tale.addPlayer(new Player(n1, tale));

				writer.writeSuccess("Member " + n1.getAsMention() + " is now a player in " + tale.getName());
			});
	}

	private void giveRole(Member m, GuildController c, Role r)
	{
		try
		{
			c.addSingleRoleToMember(m, r);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event)
	{
		if (success)
		{
			System.out.println("Command addPlayer was executed with success");
		}
		else
		{
			System.out.println("Command addPlayer could not be executed with success");
		}
		writer = null;
		membersToAdd.clear();
		membersToAdd = null;
		tale = null;
	}

	@Override
	public String help()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiscordWriter openWriter(GuildMessageReceivedEvent e)
	{
		if (writer != null)
		{
			writer = new DiscordWriter(e);
		}
		return writer;
	}

}
