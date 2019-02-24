package thdl.commands.guildMessage;


import java.util.ArrayList;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.GuildController;
import thdl.bot.DiscordMapper;
import thdl.lib.rpg.Player;
import thdl.lib.rpg.Tale;
import thdl.lib.rpg.Tales;
import thdl.util.DiscordWriter;
import thdl.util.ErrorMessages;
import thdl.util.IDiscordID;


public class CmdAddPlayer implements Command
{

	/**
	 * TODO: Rewind of the class
	 */
	private DiscordWriter writer = null;

	private ArrayList<Member> membersToAdd = null;

	private Tale tale = null;

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
				if (tale.isThisMyStoryTeller(e.getAuthor()))
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

					ArrayList<Member> helpList = new ArrayList<>();
					helpList.addAll(membersToAdd);
					for (Member m : helpList)
					{
						if (tale.isPlayerInTale(m))
						{
							membersToAdd.remove(m);
							System.out.println(m.getEffectiveName() + " removed");
							writer.writeError("Member " + m.getEffectiveName() + " already is in the Tale");
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
					System.out.println(ErrorMessages.PERMISSION_ERROR);
					writer.writeError("You are not the storyteller of this tale");
				}
			}
			else
			{
				System.out.println(ErrorMessages.CHANNEL_ERROR);
				writer.writePrivate("Wrong channel", e.getAuthor());
			}
		}
		else
		{
			System.out.println(ErrorMessages.FORMAT_ERROR);
			writer.writeInfo("You should use the layout -addPlayer [nick] ([nick] ...)");
		}

		return isOk;
	}

	private void canMemberPlay(Member m)
	{
		boolean canPlay = false;
		for (int i = 0; i < IDiscordID.rolenames.length; i++)
		{
			if (DiscordMapper.isThisA(m, IDiscordID.rolenames[i]))
			{
				canPlay = true;
				break;
			}
			else
			{
				canPlay = false;
			}
		}

		if (!canPlay)
		{
			membersToAdd.remove(m);
			System.out.println(m.getEffectiveName() + " removed");
			writer.writeError("Member " + m.getEffectiveName() + " has not the role to play");
		}

	}

	@Override
	public void action(String[] args, GuildMessageReceivedEvent e) throws Exception
	{
		GuildController controller = e.getGuild().getController();
		Role role = tale.getMyRole();
		ArrayList<Player> players = new ArrayList<>();

		for (Member m : membersToAdd)
		{
			controller.addSingleRoleToMember(m, role).queue();

			Player p = new Player(m, tale);

			players.add(p);

			p.safeNewPlayer();

			writer.writeSuccess("Member " + m.getAsMention() + " is now a player in " + tale.getName());
		}

		tale.addPlayer(players);
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
		if (writer == null)
		{
			writer = new DiscordWriter(e);
		}
		return writer;
	}

}
