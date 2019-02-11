package thdl.bot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import thdl.rpg.lib.Jobs;
import thdl.rpg.lib.Player;
import thdl.rpg.lib.PlayerCharacter;
import thdl.rpg.lib.Races;
import thdl.rpg.lib.RoundTurn;
import thdl.rpg.lib.Tale;
import thdl.rpg.lib.Tales;

public class Loader
{
	private static Loader	instance	= null;
	private DatabaseConnect	dbc			= null;

	private Loader()
	{
		dbc = DatabaseConnect.getInstance();
	}

	public static Loader getInstance()
	{
		if (instance == null)
		{
			instance = new Loader();
		}
		return instance;
	}

	public void loadTales() throws SQLException
	{

		String sql = "";
		sql = sql + "SELECT * FROM Tale";

		ResultSet rs = dbc.getData(sql);

		rs.beforeFirst();

		while (rs.next())
		{
			Tale aTale = constructTale(rs);
			Tales.addTale(aTale);
		}

	}

	private Tale constructTale(ResultSet rs) throws SQLException
	{
		String name = rs.getString(2);
		String storytellerID = rs.getString(3);
		Member storyteller = DiscordMapper.MemberOverID.get(storytellerID);

		Tale aTale = new Tale(name, storyteller);

		Guild guild = DiscordMapper.getHostServer();
		String roleID = rs.getString(7);
		String textID = rs.getString(6);
		String voiceID = rs.getString(5);
		Role role = guild.getRoleById(roleID);
		TextChannel txtchan = guild.getTextChannelById(textID);
		VoiceChannel vcchan = guild.getVoiceChannelById(voiceID);

		aTale.setBasics(role, txtchan, vcchan);

		ArrayList<Player> players = null;
		int player_nbr = 0;
		player_nbr = rs.getInt(4);

		if (player_nbr != 0)
		{
			int currentTurn = 0;
			currentTurn = rs.getInt(8);

			if (currentTurn != 0)
			{
				int maxTurn = rs.getInt(9);
				int roundCount = rs.getInt(10);

				aTale.setNumbers(currentTurn, maxTurn, roundCount);

				players = loadPlayers(aTale);
			}
			else
			{
				players = loadPlayers(aTale);
			}

			aTale.addPlayer(players);
		}

		return aTale;
	}

	private ArrayList<Player> loadPlayers(Tale tale) throws SQLException
	{
		ArrayList<Player> playersOfTale = new ArrayList<>();

		String sql = "";
		sql = sql + "SELECT * FROM Player ";
		sql = sql + "WHERE Tale = ";
		sql = sql + "(SELECT Tale_ID FROM Tale ";
		sql = sql + "WHERE Tale_Name = '" + tale.getName() + "')";

		ResultSet rs = dbc.getData(sql);

		rs.beforeFirst();

		while (rs.next())
		{
			String memberID = rs.getString(2);
			Member member = DiscordMapper.MemberOverID.get(memberID);

			Player p = new Player(member, tale);
			String characterID = rs.getString(3);
			if (characterID != null)
			{
				PlayerCharacter pc = loadPlayerCharacter(characterID, p);
				p.setMyCharacter(pc);
				tale.addCharacter(pc.getName(), p);
			}

			String turnID = rs.getString(4);
			if (turnID != null)
			{
				p.setMyTurn(loadRoundTurn(turnID, p, tale));
			}

			playersOfTale.add(p);
		}
		return playersOfTale;
	}

	private PlayerCharacter loadPlayerCharacter(String id, Player owner) throws SQLException
	{
		String name = "";
		String race = "";
		String job = "";
		int polit = 0;
		int infl = 0;
		int age = 0;
		User creator = null;

		String sql = "";
		sql = sql + "SELECT * FROM Tale_Character WHERE TCharacter_ID = '" + id + "'";

		ResultSet rs = dbc.getData(sql);

		rs.first();

		name = rs.getString(2);
		creator = DiscordMapper.getHostServer().getMemberById(rs.getString(3)).getUser();
		race = rs.getString(5);
		job = rs.getString(6);
		polit = rs.getInt(7);
		infl = rs.getInt(8);
		age = rs.getInt(9);

		PlayerCharacter pc = new PlayerCharacter(name, race, creator);
		pc.setJob(job);
		pc.setOwner(owner);
		pc.setPoliticalStand(polit);
		pc.setInfluence(infl);
		pc.setAge(age);

		loadCharacterAttributes(id, pc);
		loadCharacterAbilities(id, pc);
		loadCharacterInventory(id, pc);

		return pc;
	}

	private void loadCharacterAttributes(String id, PlayerCharacter pc) throws SQLException
	{
		String sql = "";
		sql = sql + "SELECT * FROM Chara_Attribute WHERE Rpg_Character = '" + id + "'";

		ResultSet rs = dbc.getData(sql);

		rs.beforeFirst();
		while (rs.next())
		{
			pc.addAttribute(rs.getInt(3), rs.getInt(4), rs.getInt(5));
		}
	}

	private void loadCharacterAbilities(String id, PlayerCharacter pc) throws SQLException
	{
		String sql = "";
		sql = sql + "SELECT a.Ability_Name FROM Ability a, Chara_Ability ca  WHERE ca.Rpg_Character = '" + id + "' ";
		sql = sql + "AND a.Ability_ID = ca.Ability";

		ResultSet rs = dbc.getData(sql);

		rs.beforeFirst();
		while (rs.next())
		{
			pc.addAbility(rs.getString(1));
		}
	}

	private void loadCharacterInventory(String id, PlayerCharacter pc) throws SQLException
	{
		String sql = "";
		sql = sql + "SELECT item FROM Chara_Inventory WHERE rpg_character = '" + id + "'";

		ResultSet rs = dbc.getData(sql);

		rs.beforeFirst();

		while (rs.next())
		{
			pc.addItem(rs.getString(1));
		}
	}

	private RoundTurn loadRoundTurn(String id, Player owner, Tale tale) throws SQLException
	{
		RoundTurn rt = new RoundTurn(owner, tale);

		String sql = "";
		sql = sql + "SELECT Stamina, Mana, Hunger, Thirst, isCurrent FROM Roundturn ";
		sql = sql + "WHERE Roundturn_ID = '" + id + "'";

		ResultSet rs = dbc.getData(sql);

		rs.first();

		rt.setStamina(rs.getInt(1));
		rt.setMana(rs.getInt(2));
		rt.setHunger(rs.getInt(3));
		rt.setThirst(rs.getInt(4));
		rt.setAct(rs.getBoolean(5));

		return rt;
	}

	public void loadRaces() throws SQLException
	{
		String sql = "";
		sql = sql + "SELECT Race_Name FROM Race";

		ResultSet rs = dbc.getData(sql);

		rs.beforeFirst();

		while (rs.next())
		{
			String race = rs.getString(1);
			Races.getRacesOfTHDL().add(race);
		}
	}

	public void loadRaceAges(String race, int[] ages) throws SQLException
	{
		String sql = "";
		sql = sql + "SELECT Max_Age_Old, Min_Age_Old, Max_Age_Young, Min_Age_Young FROM Race ";
		sql = sql + "WHERE race_Name = '" + race + "'";

		ResultSet rs = dbc.getData(sql);

		rs.first();
		ages[0] = rs.getInt(4);
		ages[1] = rs.getInt(3);
		ages[2] = rs.getInt(2);
		ages[3] = rs.getInt(1);
	}

	public void loadRaceAttributes(String race, String[] attribute, String ages) throws SQLException
	{
		String sql = "";
		sql = sql + "SELECT * FROM Race_Attribute ";
		sql = sql + "WHERE race = ";
		sql = sql + "(SELECT race_ID FROM Race WHERE race_Name = '" + race + "') ";
		sql = sql + "AND age = '" + ages + "'";

		ResultSet rs = dbc.getData(sql);

		rs.first();

		attribute[0] = rs.getString(4);
		attribute[1] = rs.getString(5);
		attribute[2] = rs.getString(6);
		attribute[3] = rs.getString(7);
		attribute[4] = rs.getString(8);
		attribute[5] = rs.getString(9);
		attribute[6] = rs.getString(10);
		attribute[7] = rs.getString(11);
	}

	public void loadRaceAbilities(String race, ArrayList<String> skills, ArrayList<String> diced) throws SQLException
	{
		String sql = "";
		sql = sql + "SELECT a.ability_name, ra.diced FROM Race_Ability ra, Ability a, Race r ";
		sql = sql + "WHERE ra.race = r.race_ID ";
		sql = sql + "AND a.ability_ID = ra.ability ";
		sql = sql + "AND r.race_Name = '" + race + "'";

		ResultSet rs = dbc.getData(sql);

		rs.beforeFirst();
		while (rs.next())
		{
			if (!rs.getBoolean(2))
			{
				skills.add(rs.getString(1));
			}
			else
			{
				diced.add(rs.getString(1));
			}
		}
	}

	public void loadJobs() throws SQLException
	{
		String sql = "";
		sql = sql + "SELECT Job_Name FROM Job";

		ResultSet rs = dbc.getData(sql);

		rs.beforeFirst();

		while (rs.next())
		{
			String job = rs.getString(1);
			Jobs.getStartJobs().add(job);
		}
	}

	public void loadJobAttributes(String[] attrHelp, String job) throws SQLException
	{
		String sql = "";
		sql = sql + "SELECT Attribute, Value FROM Job_Attribute ";
		sql = sql + "WHERE job = (SELECT job_id FROM Job WHERE Job_Name = '" + job + "')";

		ResultSet rs = dbc.getData(sql);

		rs.beforeFirst();

		while (rs.next())
		{
			String attr = rs.getString(1);
			String val = rs.getString(2);

			switch (attr)
			{
				case "Str":
				{
					attrHelp[0] = val;
					break;
				}
				case "Vit":
				{
					attrHelp[1] = val;
					break;
				}
				case "Int":
				{
					attrHelp[2] = val;
					break;
				}
				case "Psy":
				{
					attrHelp[3] = val;
					break;
				}
				case "Dex":
				{
					attrHelp[4] = val;
					break;
				}
				case "Led":
				{
					attrHelp[5] = val;
					break;
				}
				case "Mov":
				{
					attrHelp[6] = val;
					break;
				}
				case "Tab":
				{
					attrHelp[7] = val;
					break;
				}
				default:
				{
					System.out.println("Error");
				}
			}
		}
	}

	public void loadJobAbilities(String job, ArrayList<String> skills, ArrayList<String> diced) throws SQLException
	{
		String sql = "";
		sql = sql + "SELECT a.ability_name, ja.diced FROM Job_Ability ja, Ability a ";
		sql = sql + "WHERE ja.job = (SELECT job_ID FROM Job WHERE job_Name = '" + job + "') ";
		sql = sql + "AND a.ability_ID = ja.abilityname";

		ResultSet rs = dbc.getData(sql);

		rs.beforeFirst();
		while (rs.next())
		{
			if (!rs.getBoolean(2))
			{
				skills.add(rs.getString(1));
			}
			else
			{
				diced.add(rs.getString(1));
			}
		}
	}

	public void loadJobItems(String job, ArrayList<String> items) throws SQLException
	{
		String sql = "";
		sql = sql + "SELECT i.Item_Name FROM Item i, Job j, Job_Inventory ji ";
		sql = sql + "WHERE i.Item_ID = ji.Item AND j.Job_ID = ji.Job ";
		sql = sql + "AND j.Job_Name = '" + job + "'";

		ResultSet rs = dbc.getData(sql);

		rs.beforeFirst();
		while (rs.next())
		{
			items.add(rs.getString(1));
		}
	}

}
