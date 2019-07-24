package thdl.lib.factories.discord;


import java.awt.Color;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.managers.GuildController;
import thdl.lib.collections.discord.RoleCollection;


public class RoleFactory
{

	private static RoleFactory instance = null;

	private RoleCollection roleC = null;

	private RoleFactory()
	{
		roleC = new RoleCollection();
	}

	public static RoleFactory getInstance()
	{
		if (instance == null)
		{
			instance = new RoleFactory();
		}
		return instance;
	}

	/**
	 * Creates a List of the Roles in the connected Server
	 * 
	 * @param host
	 */
	public void createRoleMap(Guild host)
	{
		if (roleC.isEmpty())
		{
			host.getRoles().forEach(role -> roleC.addRole(role));
		}
	}

	public void resetRoleMap()
	{
		roleC.clearRoleCollection();
	}

	/**
	 * Returns the Role for the name. If nothing is found, than it returns null
	 * 
	 * @param roleName
	 * @return
	 */
	public Role getRole(String roleName)
	{
		Role r = null;
		if (roleC.containsRoleWithName(roleName))
		{
			r = roleC.getRoleByName(roleName);
		}
		return r;
	}

	/**
	 * Returns the Role for the id. If nothing is found, than it returns null
	 * 
	 * @param id
	 * @return
	 */
	public Role getRoleByID(String id)
	{
		Role r = null;
		if (roleC.containsRoleWithID(id))
		{
			r = roleC.getRoleByID(id);
		}
		return r;
	}

	/**
	 * Returns a Role-Object for the name given. If the role was not found in the
	 * Collection, it is created with the method of the GuildController-Object
	 * 
	 * @param controller
	 *            GuildController of the calling method
	 * @param roleName
	 * @return
	 * @throws Exception
	 */
	public Role createRole(GuildController controller, String roleName) throws Exception
	{
		Role r = null;
		if (roleC.containsRoleWithName(roleName))
		{
			r = roleC.getRoleByName(roleName);
		}
		else
		{
			try
			{
				r = controller.createRole().setColor(Color.GRAY).setName(roleName).complete();
				roleC.addRole(r);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return r;
	}
}
