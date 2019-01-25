package thdl.rpg.lib;

import java.util.HashMap;

public abstract class WeaponTemplates
{
	private static HashMap<String, WeaponTemplate> weaponTemplatesOverName = new HashMap<>();

	public static Weapon createWeapon(String name)
	{
		// TODO:

		WeaponTemplate template = weaponTemplatesOverName.get(name);

		Weapon w = new Weapon();

		return w;
	}
}
