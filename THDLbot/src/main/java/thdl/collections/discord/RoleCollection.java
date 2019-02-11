package thdl.collections.discord;


import net.dv8tion.jda.api.entities.Role;
import thdl.collections.Collection;


public class RoleCollection
{

	/**
	 * key1 = roleid
	 * key2 = rolename
	 * key3 = roleposition
	 */
	private Collection<String, String, Integer, Role> roleC = null;

	public RoleCollection()
	{
		roleC = new Collection<String, String, Integer, Role>();
	}

	/**
	 * Adds a role-object to the collection
	 * The first key is the Discord-specific id of the role
	 * The second key is the name of that role
	 * The third key is the position in the Guild-Role-System
	 * 
	 * @param role
	 * @return
	 */
	public boolean addRole(Role role)
	{
		String roleID = role.getId();
		String roleName = role.getName();
		Integer roleposition = role.getPosition();

		return roleC.add(role, roleID, roleName, roleposition);
	}

	public Role getRoleByID(String key)
	{
		return roleC.getByFirstKey(key);
	}

	public Role getRoleByName(String name)
	{
		return roleC.getBySecondKey(name);
	}

	public boolean containsRoleWithName(String name)
	{
		return roleC.containsSecondKey(name);
	}

	public boolean containsRoleWithID(String id)
	{
		return roleC.containsFirstKey(id);
	}

	public boolean isEmpty()
	{
		return roleC.isEmpty();
	}

	public void clearRoleCollection()
	{
		roleC.clear();
	}
}
